/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.notifygru.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.paris.lutece.plugins.workflow.modules.notifygru.business.NotifyGruHistory;
import fr.paris.lutece.plugins.workflow.modules.notifygru.business.TaskNotifyGruConfig;
import fr.paris.lutece.plugins.workflow.modules.notifygru.utils.constants.Constants;
import fr.paris.lutece.plugins.workflow.modules.notifygru.utils.constants.Constants;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


/**
 *
 * TaskNotifyGru
 *
 */
public class TaskNotifyGru extends SimpleTask
{
    private static final Pattern REMOVE_TAGS = Pattern.compile( "<.+?>" );
    private static final String _DEFAULT_VALUE_JSON = "";
    private static final int HTTP_CODE_RESPONSE_CREATED = 201;

    // SERVICES 
    @Inject
    @Named( TaskNotifyGruConfigService.BEAN_SERVICE )
    private ITaskConfigService _taskNotifyGruConfigService;
    private AbstractServiceProvider _notifyGruService;
    @Inject
    @Named( NotifyGruHistoryService.BEAN_SERVICE )
    private INotifyGruHistoryService _taskNotifyGruHistoryService;

    /**
     * {@inheritDoc}
     *
     * @param nIdResourceHistory
     * @param request
     * @param locale
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        /*Task Config*/
        TaskNotifyGruConfig config = _taskNotifyGruConfigService.findByPrimaryKey( this.getId(  ) );
        NotifyGruHistory notifyGruHistory = new NotifyGruHistory(  );
        notifyGruHistory.setIdTask( this.getId(  ) );
        notifyGruHistory.setIdResourceHistory( nIdResourceHistory );

        /*process if Task config not null and valide provider*/
        if ( ( config != null ) && ServiceConfigTaskForm.isBeanExiste( config.getIdSpringProvider(  ) ) )
        {
            //get provider
            _notifyGruService = SpringContextService.getBean( config.getIdSpringProvider(  ) );

            JSONObject fluxJson = new JSONObject(  );
            JSONObject notificationJson = this.buildJsonGlobal( config, nIdResourceHistory, locale );

            //if active config for Mail : user_email
            String strMessageEmail = _DEFAULT_VALUE_JSON;

            if ( config.isActiveOngletEmail(  ) )
            {
                JSONObject userEmailJson = this.buildJsonMessageEmail( config, nIdResourceHistory, locale );
                notificationJson.accumulate( Constants.MARK_USER_MAIL, userEmailJson );
                strMessageEmail = userEmailJson.getString( Constants.MARK_MESSAGE_EMAIL );
            }

            //if active config for Desk : user_dashboard
            String strMessageGuichet = "";

            if ( config.isActiveOngletGuichet(  ) )
            {
                JSONObject userDashBoardJson = this.buildJsonMessageGuichet( config, nIdResourceHistory, locale );
                notificationJson.accumulate( Constants.MARK_USER_DASHBOARD, userDashBoardJson );
                strMessageGuichet = userDashBoardJson.getString( Constants.MARK_MESSAGE_USERDASHBOARD );
            }

            //if active config for SMS : user_sms
            String strMessageSMS = "";

            if ( config.isActiveOngletSMS(  ) &&
                    !_notifyGruService.getOptionalMobilePhoneNumber( nIdResourceHistory )
                                          .equals( Constants.OPTIONAL_STRING_VALUE ) )
            {
                JSONObject smsJson = this.buildJsonMessageSMS( config, nIdResourceHistory, locale );
                notificationJson.accumulate( Constants.MARK_USER_SMS, smsJson );
                strMessageSMS = smsJson.getString( Constants.MARK_MESSAGE_SMS );
            }

            //if active config for Agent : backoffice_logging
            String strMessageAgent = "";

            if ( config.isActiveOngletAgent(  ) )
            {
                JSONObject backOfficeLogginJson = this.buildJsonMessageBackOfficeLoggin( strMessageSMS,
                        strMessageGuichet, strMessageEmail, config, nIdResourceHistory, locale );
                notificationJson.accumulate( Constants.MARK_BACK_OFFICE_LOGGING, backOfficeLogginJson );
                strMessageAgent = backOfficeLogginJson.getString( Constants.MARK_MESSAGE_BACK_OFFICE_LOGGING );
            }

            //populate email data for history
            notifyGruHistory.setEmail( NotificationToHistory.populateEmail( config, strMessageEmail ) );

            //populate desk data for history
            notifyGruHistory.setGuichet( NotificationToHistory.populateGuichet( config, strMessageGuichet ) );

            //populate sms data for history
            notifyGruHistory.setSMS( NotificationToHistory.populateSMS( config, strMessageSMS ) );

            //populate Broadcast data for history
            notifyGruHistory.setBroadCast( NotificationToHistory.populateBroadcast( config, strMessageGuichet ) );

            //populate Broadcast data for history
            notifyGruHistory.setAgent( NotificationToHistory.populateAgent( config, strMessageAgent ) );

            //build JSON notification
            fluxJson.accumulate( Constants.MARK_NOTIFICATION, notificationJson );

            String strJson = fluxJson.toString( 2 );
            String strToken = AppPropertiesService.getProperty( Constants.TOKEN );

            try
            {
                if ( StringUtils.isNotBlank( strToken ) )
                {
                    JSONObject tokenAuth = getToken(  );
                    sendNotificationsAsJson( strJson, tokenAuth );
                }
                else
                {
                    sendNotificationsAsJson( strJson );
                }

                _taskNotifyGruHistoryService.create( notifyGruHistory, WorkflowUtils.getPlugin(  ) );
            }
            catch ( Exception e )
            {
                AppLogService.error( "Error sending JSON to Notification EndPoint : " + e.getMessage(  ), e );
                throw new AppException( e.getMessage(  ), e );
            }
        }
    }

    /**
     * @param config the config
     * @param nIdResourceHistory thr ressource history
     * @param locale the local of request
     *  * @return JSONObject Global
     *  */
    private JSONObject buildJsonGlobal( TaskNotifyGruConfig config, int nIdResourceHistory, Locale locale )
    {
        JSONObject notificationJson = new JSONObject(  );
        notificationJson.accumulate( Constants.MARK_USER_GUID, _notifyGruService.getUserGuid( nIdResourceHistory ) );
        notificationJson.accumulate( Constants.MARK_EMAIL, _notifyGruService.getUserEmail( nIdResourceHistory ) );
        notificationJson.accumulate( Constants.MARK_CRM_STATUS_ID, config.getCrmStatusIdCommune(  ) );
        notificationJson.accumulate( Constants.MARK_NOTIFICATION_TYPE, _DEFAULT_VALUE_JSON );

        int nIdDemand = _notifyGruService.getOptionalDemandId( nIdResourceHistory );
        notificationJson.accumulate( Constants.MARK_ID_DEMAND,
            ( ( nIdDemand != Constants.OPTIONAL_INT_VALUE )
            ? _notifyGruService.getOptionalDemandId( nIdResourceHistory ) : _DEFAULT_VALUE_JSON ) );

        int nIdDemandType = _notifyGruService.getOptionalDemandIdType( nIdResourceHistory );
        notificationJson.accumulate( Constants.MARK_ID_DEMAND_TYPE,
            ( ( nIdDemandType != Constants.OPTIONAL_INT_VALUE ) ? 1 : _DEFAULT_VALUE_JSON ) );
        notificationJson.accumulate( Constants.MARK_DEMAND_MAX_STEP,
            ( ( config.getDemandMaxStepGuichet(  ) >= 0 ) ? config.getDemandMaxStepGuichet(  ) : _DEFAULT_VALUE_JSON ) );
        notificationJson.accumulate( Constants.MARK_DEMAND_USER_CURRENT_STEP,
            ( ( config.getDemandMaxStepGuichet(  ) >= 0 ) ? config.getDemandUserCurrentStepGuichet(  )
                                                          : _DEFAULT_VALUE_JSON ) );
        notificationJson.accumulate( Constants.MARK_DEMAND_STATE,
            ( ( config.getDemandStateGuichet(  ) >= 0 ) ? config.getDemandStateGuichet(  ) : _DEFAULT_VALUE_JSON ) );

        return notificationJson;
    }

    /**
     * @param config the config
     * @param nIdResourceHistory thr ressource history
     * @param locale the local of request
     *  * @return JSONObject Guichet
     *  */
    private JSONObject buildJsonMessageGuichet( TaskNotifyGruConfig config, int nIdResourceHistory, Locale locale )
    {
        JSONObject userDashBoardJson = new JSONObject(  );
        HtmlTemplate tMessageUserDashboard = AppTemplateService.getTemplateFromStringFtl( config.getMessageGuichet(  ),
                locale, _notifyGruService.getInfos( nIdResourceHistory ) );

        userDashBoardJson.accumulate( Constants.MARK_STATUS_TEXT_USERDASHBOARD, config.getStatustextGuichet(  ) );
        userDashBoardJson.accumulate( Constants.MARK_SENDER_NAME_USERDASHBOARD, config.getSenderNameGuichet(  ) );
        userDashBoardJson.accumulate( Constants.MARK_SUBJECT_USERDASHBOARD, config.getSubjectGuichet(  ) );
        userDashBoardJson.accumulate( Constants.MARK_MESSAGE_USERDASHBOARD,
            this.getHTMLEntities( tMessageUserDashboard.getHtml(  ) ) );
        userDashBoardJson.accumulate( Constants.MARK_DATA_USERDASHBOARD, _DEFAULT_VALUE_JSON );

        return userDashBoardJson;
    }

    /**
     * @param strMessageSMS initialization message of SMS
     * * @param strMessageGuichet initialization message of Guichet
     * * @param strMessageEmail initialization message of Email
     * @param config the config
     * @param nIdResourceHistory thr ressource history
     * @param locale the local of request
     *  * @return JSONObject Backoffice
     *  */
    private JSONObject buildJsonMessageBackOfficeLoggin( String strMessageSMS, String strMessageGuichet,
        String strMessageEmail, TaskNotifyGruConfig config, int nIdResourceHistory, Locale locale )
    {
        JSONObject backOfficeLogginJson = new JSONObject(  );
        backOfficeLogginJson.accumulate( Constants.MARK_STATUS_TEXT_BACK_OFFICE_LOGGING, config.getStatustextGuichet(  ) );

        HtmlTemplate tMessageAgent = AppTemplateService.getTemplateFromStringFtl( config.getMessageAgent(  ), locale,
                _notifyGruService.getInfos( nIdResourceHistory ) );
        String strMessageAgent = this.getHTMLEntities( tMessageAgent.getHtml(  ) );

        backOfficeLogginJson.accumulate( Constants.MARK_MESSAGE_BACK_OFFICE_LOGGING, strMessageAgent );
        backOfficeLogginJson.accumulate( Constants.MARK_NOTIFIED_ON_DASHBOARD,
            ( config.isActiveOngletGuichet(  ) ) ? 1 : 0 );
        backOfficeLogginJson.accumulate( Constants.MARK_NOTIFIED_BY_EMAIL, ( config.isActiveOngletEmail(  ) ) ? 1 : 0 );
        backOfficeLogginJson.accumulate( Constants.MARK_NOTIFIED_BY_SMS, ( config.isActiveOngletSMS(  ) ) ? 1 : 0 );

        if ( config.isActiveOngletGuichet(  ) )
        {
            backOfficeLogginJson.accumulate( Constants.MARK_DISPLAY_LEVEL_DASHBOARD_NOTIFICATION,
                config.getLevelNotificationGuichet(  ) );
            backOfficeLogginJson.accumulate( Constants.MARK_VIEW_DASHBOARD_NOTIFICATION,
                Constants.MARK_DISPLAY_MESSAGE + " " + strMessageGuichet );
        }

        if ( config.isActiveOngletEmail(  ) )
        {
            backOfficeLogginJson.accumulate( Constants.MARK_DISPLAY_LEVEL_EMAIL_NOTIFICATION,
                config.getLevelNotificationEmail(  ) );
            backOfficeLogginJson.accumulate( Constants.MARK_VIEW_EMAIL_NOTIFICATION,
                Constants.MESSAGE_DISPLAY_EMAIL + " " + _notifyGruService.getUserEmail( nIdResourceHistory ) +
                Constants.MESSAGE_DISPLAY_OBJECT + " " + config.getSubjectEmail(  ) +
                Constants.MESSAGE_DISPLAY_MESSAGE_EMAIL + " " + strMessageEmail );
        }

        if ( config.isActiveOngletSMS(  ) &&
                !_notifyGruService.getOptionalMobilePhoneNumber( nIdResourceHistory )
                                      .equals( Constants.OPTIONAL_STRING_VALUE ) )
        {
            backOfficeLogginJson.accumulate( Constants.MARK_DISPLAY_LEVEL_SMS_NOTIFICATION,
                config.getLevelNotificationSMS(  ) );
            backOfficeLogginJson.accumulate( Constants.MARK_VIEW_SMS_NOTIFICATION,
                Constants.MESSAGE_DISPLAY_SMS + " " +
                _notifyGruService.getOptionalMobilePhoneNumber( nIdResourceHistory ) +
                Constants.MESSAGE_DISPLAY_MESSAGE_SMS + " " + strMessageSMS );
        }

        return backOfficeLogginJson;
    }

    /**
     * @param config the config
     * @param nIdResourceHistory thr ressource history
     * @param locale the local of request
     *  * @return JSONObject SMS
     *  */
    private JSONObject buildJsonMessageSMS( TaskNotifyGruConfig config, int nIdResourceHistory, Locale locale )
    {
        JSONObject smsJson = new JSONObject(  );
        HtmlTemplate tMessageSMS = AppTemplateService.getTemplateFromStringFtl( config.getMessageSMS(  ), locale,
                _notifyGruService.getInfos( nIdResourceHistory ) );
        smsJson.accumulate( Constants.MARK_PHONE_NUMBER,
            _notifyGruService.getOptionalMobilePhoneNumber( nIdResourceHistory ) );
        smsJson.accumulate( Constants.MARK_MESSAGE_SMS, tMessageSMS.getHtml(  ) );

        return smsJson;
    }

    /**
     * @param config the config
     * @param nIdResourceHistory thr ressource history
     * @param locale the local of request
     * @return JSONObject Mail
     * */
    private JSONObject buildJsonMessageEmail( TaskNotifyGruConfig config, int nIdResourceHistory, Locale locale )
    {
        JSONObject userEmailJson = new JSONObject(  );
        HtmlTemplate tMessageEmail = AppTemplateService.getTemplateFromStringFtl( config.getMessageEmail(  ), locale,
                _notifyGruService.getInfos( nIdResourceHistory ) );
        userEmailJson.accumulate( Constants.MARK_SENDER_NAME, config.getSenderNameEmail(  ) );
        userEmailJson.accumulate( Constants.MARK_SENDER_EMAIL, MailService.getNoReplyEmail(  ) );
        userEmailJson.accumulate( Constants.MARK_RECIPIENT, _notifyGruService.getUserEmail( nIdResourceHistory ) );
        userEmailJson.accumulate( Constants.MARK_SUBJECT, config.getSubjectEmail(  ) );
        userEmailJson.accumulate( Constants.MARK_MESSAGE_EMAIL, this.getHTMLEntities( tMessageEmail.getHtml(  ) ) );
        userEmailJson.accumulate( Constants.MARK_CC,
            ( StringUtils.isNotBlank( config.getRecipientsCcEmail(  ) ) ? config.getRecipientsCcEmail(  ) : "" ) );
        userEmailJson.accumulate( Constants.MARK_CCI,
            ( StringUtils.isNotBlank( config.getRecipientsCciEmail(  ) ) ? config.getRecipientsCcEmail(  ) : "" ) );

        return userEmailJson;
    }

    /**
     * @param htmlData to encode
     * @return String without entities HTML
     * */
    private String getHTMLEntities( String htmlData )
    {
        //        htmlData = StringEscapeUtils.unescapeHtml4(htmlData);
        //        AppLogService.info("Apres unescapeHtml4 \n" + htmlData);
        //        htmlData = removeTags(htmlData);
        return htmlData;
    }

    /**
     * @param string to remove tag
     * @return String without html tag
     * */
    public static String removeTags( String string )
    {
        if ( ( string == null ) || ( string.length(  ) == 0 ) )
        {
            return string;
        }

        Matcher m = REMOVE_TAGS.matcher( string );

        return m.replaceAll( "" );
    }

    /**
     * @param strJson json to send
      * @param tokenAuth token to auth in API Manager
     * */
    private void sendNotificationsAsJson( String strJson, JSONObject tokenAuth )
    {
        Client client = Client.create(  );

        WebResource webResource = client.resource( AppPropertiesService.getProperty( 
                    Constants.URL_NOTIFICATION_ENDPOINT ) );

        ClientResponse response = webResource.type( Constants.CONTENT_FORMAT )
                                             .header( HttpHeaders.AUTHORIZATION,
                Constants.TYPE_AUTHENTIFICATION + " " + (String) tokenAuth.get( Constants.PARAMS_ACCES_TOKEN ) )
                                             .accept( MediaType.APPLICATION_JSON ).post( ClientResponse.class, strJson );

        if ( response.getStatus(  ) != HTTP_CODE_RESPONSE_CREATED )
        {
            throw new AppException( Constants.ERROR_MESSAGE + response.getStatus(  ) );
        }
    }

    private void sendNotificationsAsJson( String strJson )
    {
        AppLogService.debug( "Sending notification as JSON : " + strJson );

        Client client = Client.create(  );

        WebResource webResource = client.resource( AppPropertiesService.getProperty( 
                    Constants.URL_NOTIFICATION_ENDPOINT ) );

        ClientResponse response = webResource.type( Constants.CONTENT_FORMAT ).accept( MediaType.APPLICATION_JSON )
                                             .post( ClientResponse.class, strJson );

        if ( response.getStatus(  ) != HTTP_CODE_RESPONSE_CREATED )
        {
            throw new AppException( Constants.ERROR_MESSAGE + response.getStatus(  ) );
        }
    }

    /**
     *
     * @return JSONObject auth to API manager
     * */
    private JSONObject getToken(  )
    {
        Client client = Client.create(  );

        WebResource webResource = client.resource( AppPropertiesService.getProperty( Constants.URL_TOKEN ) );

        javax.ws.rs.core.MultivaluedMap<String, String> params = new com.sun.jersey.core.util.MultivaluedMapImpl(  );
        params.add( Constants.PARAMS_GRANT_TYPE, Constants.PARAMS_GRANT_TYPE_VALUE );

        ClientResponse response = webResource.type( Constants.CONTENT_FORMAT_TOKEN )
                                             .header( HttpHeaders.AUTHORIZATION,
                Constants.TYPE_AUTHENTIFICATION + " " + AppPropertiesService.getProperty( Constants.TOKEN ) )
                                             .accept( MediaType.APPLICATION_JSON ).post( ClientResponse.class, params );

        String output = response.getEntity( String.class );

        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( output );

        return jsonObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRemoveConfig(  )
    {
        _taskNotifyGruConfigService.remove( this.getId(  ) );
        _taskNotifyGruHistoryService.removeByTask( this.getId(  ), WorkflowUtils.getPlugin(  ) );
    }

    /**
    * {@inheritDoc}
    * @param nIdHistory
    */
    @Override
    public void doRemoveTaskInformation( int nIdHistory )
    {
        _taskNotifyGruHistoryService.removeByHistory( nIdHistory, this.getId(  ), WorkflowUtils.getPlugin(  ) );
    }

    /**
     * {@inheritDoc}
     * @param locale
     * @return
     */
    @Override
    public String getTitle( Locale locale )
    {
        TaskNotifyGruConfig config = _taskNotifyGruConfigService.findByPrimaryKey( this.getId(  ) );

        if ( config != null )
        {
            return I18nService.getLocalizedString( Constants.TITLE_NOTIFY, locale );
        }

        return I18nService.getLocalizedString( Constants.TITLE_NOTIFY, locale );
    }
}
