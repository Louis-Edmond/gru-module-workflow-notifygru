package fr.paris.lutece.plugins.workflow.modules.notifygru.web;


import fr.paris.lutece.plugins.workflow.modules.notifygru.business.TaskNotifyGruConfig;

import fr.paris.lutece.plugins.workflow.modules.notifygru.service.TaskNotifyGruConfigService;
import fr.paris.lutece.plugins.workflow.modules.notifygru.utils.constants.NotifyGruConstants;
import fr.paris.lutece.plugins.workflow.service.WorkflowPlugin;
import fr.paris.lutece.plugins.workflow.service.security.IWorkflowUserAttributesManager;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import fr.paris.lutece.plugins.workflow.modules.notifygru.service.AbstractServiceProvider;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.INotifyGruService;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * INotifyGruTaskComponent
 *
 */
public class NotifyGruTaskComponent extends NoFormTaskComponent {

    // TEMPLATES
    private static final String TEMPLATE_TASK_NOTIFY_GRU_CONFIG = "admin/plugins/workflow/modules/notifygru/task_notify_gru_config.html";

    // SERVICES
    @Inject
    @Named(TaskNotifyGruConfigService.BEAN_SERVICE)
    private ITaskConfigService _taskNotifyGruConfigService;
    @Inject
    private INotifyGruService _notifyGRUService;
    @Inject
    private IWorkflowUserAttributesManager _userAttributesManager;
    
    
    AbstractServiceProvider _mokeProviderService;
  // AbstractServiceProvider _mokeProviderService =  new Mook1ProviderService();

    /**
     * {@inheritDoc}
     */
    @Override
    public String doSaveConfig(HttpServletRequest request, Locale locale, ITask task) {
        String strApply = request.getParameter(NotifyGruConstants.PARAMETER_APPY);
        String strOngletActive = request.getParameter(NotifyGruConstants.PARAMETER_ONGLET);
        String strProvider = request.getParameter(NotifyGruConstants.PARAMETER_SELECT_PROVIDER);
        TaskNotifyGruConfig config = _taskNotifyGruConfigService.findByPrimaryKey(task.getId());

        Boolean bActiveOngletGuichet = (config.getIdTask() == 0) ? false : config.isActiveOngletGuichet();
        Boolean bActiveOngletAgent = (config.getIdTask() == 0) ? false : config.isActiveOngletAgent();
        Boolean bActiveOngletEmail = (config.getIdTask() == 0) ? false : config.isActiveOngletEmail();
        Boolean bActiveOngletSMS = (config.getIdTask() == 0) ? false : config.isActiveOngletSMS();
        Boolean bActiveOngletBROADCAST = (config.getIdTask() == 0) ? false : config.isActiveOngletBroadcast();

        
         int nIdResource=1;
        if(strProvider!=null){
           
            config.setIdRessource(nIdResource);          
           
        }else if(!bActiveOngletGuichet && !bActiveOngletAgent && !bActiveOngletEmail && !bActiveOngletSMS && !bActiveOngletBROADCAST){
         
            if(strApply.equals("")){
              Object[] tabRequiredFields = {I18nService.getLocalizedString(NotifyGruConstants.MESSAGE_MANDATORY_PROVIDER, locale)};

            return AdminMessageService.getMessageUrl(request, NotifyGruConstants.MESSAGE_MANDATORY_PROVIDER,
               tabRequiredFields, AdminMessage.TYPE_STOP);
            }
        }
        
        
        if(strApply!=null) {
        switch (strApply) {
            case NotifyGruConstants.PARAMETER_BUTTON_ADD:
                bActiveOngletGuichet = (NotifyGruConstants.MARK_ONGLET_GUICHET.equals(strOngletActive)) ? true : false;
                bActiveOngletAgent = (NotifyGruConstants. MARK_ONGLET_AGENT.equals(strOngletActive)) ? true : false;
                bActiveOngletEmail = (NotifyGruConstants.MARK_ONGLET_EMAIL.equals(strOngletActive)) ? true : false;
                bActiveOngletSMS = (NotifyGruConstants.MARK_ONGLET_SMS.equals(strOngletActive)) ? true : false;
                bActiveOngletBROADCAST = (NotifyGruConstants.MARK_ONGLET_LIST.equals(strOngletActive)) ? true : false;
                break;
            case NotifyGruConstants.PARAMETER_BUTTON_REMOVE:
                bActiveOngletGuichet = (NotifyGruConstants. MARK_ONGLET_GUICHET.equals(strOngletActive)) ? false : config.isActiveOngletGuichet();
                bActiveOngletAgent = (NotifyGruConstants. MARK_ONGLET_AGENT.equals(strOngletActive)) ? false : config.isActiveOngletAgent();
                bActiveOngletEmail = (NotifyGruConstants.MARK_ONGLET_EMAIL.equals(strOngletActive)) ? false : config.isActiveOngletEmail();
                bActiveOngletSMS = (NotifyGruConstants.MARK_ONGLET_SMS.equals(strOngletActive)) ? false : config.isActiveOngletSMS();
                bActiveOngletBROADCAST = (NotifyGruConstants.MARK_ONGLET_LIST.equals(strOngletActive)) ? false : config.isActiveOngletBroadcast();
                break;
        }
       }
        if(strApply==null && !bActiveOngletAgent && !bActiveOngletBROADCAST && 
                !bActiveOngletEmail && !bActiveOngletGuichet &&!bActiveOngletSMS && strProvider==null) {
            
             Object[] tabRequiredFields = {I18nService.getLocalizedString(NotifyGruConstants.MESSAGE_MANDATORY_ONGLET, locale)};

            return AdminMessageService.getMessageUrl(request, NotifyGruConstants.MESSAGE_MANDATORY_ONGLET,
               tabRequiredFields, AdminMessage.TYPE_STOP);
        }
    
        if (bActiveOngletGuichet || (strApply!=null && strApply.equals(NotifyGruConstants.PARAMETER_BUTTON_REMOVE) && NotifyGruConstants.MARK_ONGLET_GUICHET.equals(strOngletActive))) {

            /*général*/
        	ArrayList<String> _errors = new ArrayList<String>();
           /* String strIdResource = request.getParameter(NotifyGruConstants.PARAMETER_ID_RESOURCE);
            int nIdResource = (strIdResource == null) ? WorkflowUtils.CONSTANT_ID_NULL : Integer.parseInt(strIdResource);
            String stridUserGuid = request.getParameter(NotifyGruConstants.PARAMETER_ID_USER_GUID);//non
            int nstridUserGuid = (stridUserGuid == null) ? WorkflowUtils.CONSTANT_ID_NULL : Integer.parseInt(stridUserGuid);*/
            /*fin général*/

            if (StringUtils.isBlank(strApply)) {
                if (nIdResource == WorkflowUtils.CONSTANT_ID_NULL) {
                   // strError = "IdResource";
                }
            }

            /*guichet*/
            int nidDemandGuichet=-1;// A SUPPRIMER
            /*String stridDemandGuichet = request.getParameter(NotifyGruConstants.PARAMETER_ID_DEMAND_GUICHET);
            int nidDemandGuichet = (stridDemandGuichet == null) ? WorkflowUtils.CONSTANT_ID_NULL : Integer.parseInt(stridDemandGuichet);
			*/
			
            if (StringUtils.isBlank(strApply)) {
                if (nidDemandGuichet == WorkflowUtils.CONSTANT_ID_NULL) {
                   // strError = "IdDemande";
                }

            }
            
            int nCrmWebAppCodeGuichet=1;
            /*String strCrmWebAppCodeGuichet = request.getParameter(NotifyGruConstants.PARAMETER_CRM_WEBAPP_CODE_GUICHET);
            int nCrmWebAppCodeGuichet = (strCrmWebAppCodeGuichet == null) ? WorkflowUtils.CONSTANT_ID_NULL : Integer.parseInt(strCrmWebAppCodeGuichet);
			*/
			
            if (StringUtils.isBlank(strApply)) {
                if (nCrmWebAppCodeGuichet == WorkflowUtils.CONSTANT_ID_NULL) {
                   // strError = "nCrmWebAppCodeGuichet";
                }

            }

            /*String strSendNotificationGuichet = request.getParameter(NotifyGruConstants.PARAMETER_SEND_NOTIFICATION_GUICHET);
            Boolean bSendNotificationGuichet = Boolean.parseBoolean(strSendNotificationGuichet);
			*/

            String strStatusTextGuichet = "status";
           // String strStatusTextGuichet = request.getParameter(NotifyGruConstants.PARAMETER_STATUS_TEXT_GUICHET);
            String strSubjectGuichet = request.getParameter(NotifyGruConstants.PARAMETER_SUBJECT_GUICHET);
            String strMessageGuichet = request.getParameter(NotifyGruConstants.PARAMETER_MESSAGE_GUICHET);
            String strSenderNameGuichet = request.getParameter(NotifyGruConstants.PARAMETER_SENDER_NAME_GUICHET);
            String strLevelNotificationGuichet = request.getParameter(NotifyGruConstants.PARAMETER_LEVEL_NOTIFICATION_GUICHET);

            if (StringUtils.isBlank(strApply)) {
               
                       // || strMessageGuichet == WorkflowUtils.EMPTY_STRING
                       // || strSubjectGuichet == WorkflowUtils.EMPTY_STRING
                        //|| strLevelNotificationGuichet == WorkflowUtils.EMPTY_STRING
                        //|| strStatusTextGuichet == WorkflowUtils.EMPTY_STRING) 
                
                if(strSubjectGuichet == WorkflowUtils.EMPTY_STRING)
                {
                	_errors.add(NotifyGruConstants.MESSAGE_GUICHET_SUBJECT_FIELD); 
                }
                if(strMessageGuichet == WorkflowUtils.EMPTY_STRING)
                {
                	_errors.add(NotifyGruConstants.MESSAGE_GUICHET_MESSAGE_FIELD) ;
                }

            }
            
            if (_errors.size()!=0) 
            {
              return this.displayErrorMessage(_errors, request);
            }

            config.setIdRessource(nIdResource);
           // config.setIdUserGuid(nstridUserGuid);
            config.setIdDemandGuichet(nidDemandGuichet);
            config.setCrmWebAppCodeGuichet(nCrmWebAppCodeGuichet);
           // config.setSendNotificationGuichet(bSendNotificationGuichet);
            config.setStatusTextGuichet(strStatusTextGuichet);
            config.setSubjectGuichet(strSubjectGuichet);
            config.setMessageGuichet(strMessageGuichet);
            config.setSenderNameGuichet(strSenderNameGuichet);
            config.setLevelNotificationGuichet(strLevelNotificationGuichet);
            config.setActiveOngletGuichet(bActiveOngletGuichet);

            /*fin guichet*/
        }

        if (bActiveOngletAgent || (strApply!=null && strApply.equals(NotifyGruConstants.PARAMETER_BUTTON_REMOVE) && NotifyGruConstants.MARK_ONGLET_AGENT.equals(strOngletActive))) {

            /*Agent*/
        	ArrayList<String> _errors = new ArrayList<String>();
            //String strStatusTextAgent = request.getParameter(NotifyGruConstants.PARAMETER_STATUS_TEXT_AGENT);
            String strMessageAgent = request.getParameter(NotifyGruConstants.PARAMETER_STATUS_MESSAGE_AGENT);
            String strLevelNotificationAgent = request.getParameter(NotifyGruConstants.PARAMETER_LEVEL_NOTIFICATION_AGENT);
            
            if(StringUtils.isBlank(strApply))
            		{
            			if(strMessageAgent == WorkflowUtils.EMPTY_STRING)
            			{
            				 _errors.add(NotifyGruConstants.MESSAGE_AGENT_FIELD);
            			}
            		}
            if (_errors.size()!=0) 
            {
            	return this.displayErrorMessage(_errors, request);
            }
          /*  if (StringUtils.isBlank(strApply)) {
                if (strStatusTextAgent == WorkflowUtils.EMPTY_STRING
                        || strMessageAgent == WorkflowUtils.EMPTY_STRING
                        || strLevelNotificationAgent == WorkflowUtils.EMPTY_STRING) {
                    strError = NotifyGruConstants.MESSAGE_MANDATORY_FIELD;
                }

            }*/

            //config.setStatusTextAgent(strStatusTextAgent);
            config.setMessageAgent(strMessageAgent);
            config.setLevelNotificationAgent(strLevelNotificationAgent);
            config.setActiveOngletAgent(bActiveOngletAgent);

            /*Fin Agent*/
        }

        if (bActiveOngletEmail || (strApply!=null && strApply.equals(NotifyGruConstants.PARAMETER_BUTTON_REMOVE) && NotifyGruConstants.MARK_ONGLET_EMAIL.equals(strOngletActive))) {
            /*email*/
        	ArrayList<String> _errors = new ArrayList<String>();
            //String strRessourceRecordEmail = request.getParameter(NotifyGruConstants.PARAMETER_RESOURCE_RECORD_EMAIL);
            String strSubjectEmail = request.getParameter(NotifyGruConstants.PARAMETER_SUBJECT_EMAIL);
           // String strEntryEmail = request.getParameter(NotifyGruConstants.PARAMETER_ENTRY_EMAIL); //NON  
            String strMessageEmail = request.getParameter(NotifyGruConstants.PARAMETER_MESSAGE_EMAIL);
            String strSenderNameEmail = request.getParameter(NotifyGruConstants.PARAMETER_SENDER_NAME_EMAIL);

            //String strRecipientsCcEmail = request.getParameter(NotifyGruConstants.PARAMETER_RECIPIENT_CC_EMAIL);
            //String strRecipientsCciEmail = request.getParameter(NotifyGruConstants.PARAMETER_RECIPIENT_CCI_EMAIL);
            String strLevelNotificationEmail = request.getParameter(NotifyGruConstants.PARAMETER_LEVEL_NOTIFICATION_EMAIL);

            if (StringUtils.isBlank(strApply)) 
            {
            	if(strSenderNameEmail == WorkflowUtils.EMPTY_STRING)
            	{
            		_errors.add(NotifyGruConstants.MESSAGE_EMAIL_SENDER_NAME_FIELD);
            	}
            	if(strSubjectEmail == WorkflowUtils.EMPTY_STRING)
            	{
            		_errors.add(NotifyGruConstants.MESSAGE_EMAIL_SUBJECT_FIELD);
            	}
            	if(strMessageEmail == WorkflowUtils.EMPTY_STRING)
            	{
            		_errors.add(NotifyGruConstants.MESSAGE_EMAIL_MESSAGE_FIELD);
            	}
            	/* if (strRessourceRecordEmail == WorkflowUtils.EMPTY_STRING
                || strSubjectEmail == WorkflowUtils.EMPTY_STRING
               || strEntryEmail == WorkflowUtils.EMPTY_STRING
             || Validator.isEmailValid(strEntryEmail) == false
                || strMessageEmail == WorkflowUtils.EMPTY_STRING
                || strSenderNameEmail == WorkflowUtils.EMPTY_STRING
               || strRecipientsCcEmail == WorkflowUtils.EMPTY_STRING
                || Validator.isRecipientCcValid(strRecipientsCcEmail) == false
               || strRecipientsCciEmail == WorkflowUtils.EMPTY_STRING
               || Validator.isRecipientCcValid(strRecipientsCciEmail) == false
                || strLevelNotificationEmail == WorkflowUtils.EMPTY_STRING) {
            strError = NotifyGruConstants.MESSAGE_MANDATORY_FIELD;
        }*/
          }
            if (_errors.size()!=0) 
            {
            	return this.displayErrorMessage(_errors, request);
            }
           // config.setRessourceRecordEmail(strRessourceRecordEmail);
            config.setSubjectEmail(strSubjectEmail);
            //config.setEntryEmail(strEntryEmail);
            config.setMessageEmail(strMessageEmail);
            config.setSenderNameEmail(strSenderNameEmail);

            //config.setRecipientsCcEmail(strRecipientsCcEmail);
            //config.setRecipientsCciEmail(strRecipientsCciEmail);

            config.setLevelNotificationEmail(strLevelNotificationEmail);
            config.setActiveOngletEmail(bActiveOngletEmail);

            /*fin email*/
        }

        if (bActiveOngletSMS || (strApply!=null && strApply.equals(NotifyGruConstants.PARAMETER_BUTTON_REMOVE) && NotifyGruConstants.MARK_ONGLET_SMS.equals(strOngletActive))) {
            /*sms*/
        	ArrayList<String> _errors = new ArrayList<String>();
           // String strRessourceRecordSMS = request.getParameter(NotifyGruConstants.PARAMETER_RESOURCE_RECORD_SMS);

            //String strPhoneSMS = request.getParameter(NotifyGruConstants.PARAMETER_PHONE_SMS);
            String strMessageSMS = request.getParameter(NotifyGruConstants.PARAMETER_MESSAGE_SMS);
            String strLevelNotificationSMS = request.getParameter(NotifyGruConstants.PARAMETER_LEVEL_NOTIFICATION_SMS);

            if (StringUtils.isBlank(strApply)) {
               /* if (strRessourceRecordSMS == WorkflowUtils.EMPTY_STRING
                        || strPhoneSMS == WorkflowUtils.EMPTY_STRING
                        || strMessageSMS == WorkflowUtils.EMPTY_STRING
                        || Validator.isSMSvalid(strMessageSMS) == false
                        || strLevelNotificationSMS == WorkflowUtils.EMPTY_STRING) {
                    strError = NotifyGruConstants.MESSAGE_MANDATORY_FIELD;
                }*/
            	if(strMessageSMS == WorkflowUtils.EMPTY_STRING)
            	{
            		_errors.add(NotifyGruConstants.MESSAGE_SMS_FIELD);
            	}

            }
            if (_errors.size()!=0) 
            {
            	return this.displayErrorMessage(_errors, request);
            }

           // config.setRessourceRecordSMS(strRessourceRecordSMS);
            //config.setPhoneSMS(strPhoneSMS);
            config.setMessageSMS(strMessageSMS);

            config.setLevelNotificationSMS(strLevelNotificationSMS);
            config.setActiveOngletSMS(bActiveOngletSMS);

            /*fin sms*/
        }

        if (bActiveOngletBROADCAST || (strApply!=null && strApply.equals(NotifyGruConstants.PARAMETER_BUTTON_REMOVE) && NotifyGruConstants.MARK_ONGLET_LIST.equals(strOngletActive))) {
        	ArrayList<String> _errors = new ArrayList<String>();
        	// String strIdMailingListBroadcast = request.getParameter(NotifyGruConstants.PARAMETER_ID_MAILING_LIST);
            //int nIdMailingListBroadcast = (strIdMailingListBroadcast == null) ? WorkflowUtils.CONSTANT_ID_NULL : Integer.parseInt(strIdMailingListBroadcast);
        	int nIdMailingListBroadcast =1 ;// A SUPPRIMER
        	
            String strsenderNameBroadcast = request.getParameter(NotifyGruConstants.PARAMETER_SENDER_NAME_BROADCAST);
            String strsubjectBroadcast = request.getParameter(NotifyGruConstants.PARAMETER_SUBJECT_BROADCAST);
            String strmessageBroadcast = request.getParameter(NotifyGruConstants.PARAMETER_MESSAGE_BROADCAST);
           // String strrecipientsCcBroadcast = request.getParameter(NotifyGruConstants.PARAMETER_RECIPIENT_CC_BROADCAST);
            //String strrecipientsCciBroadcast = request.getParameter(NotifyGruConstants.PARAMETER_RECIPIENT_CCI_BROADCAST);
            String strLevelNotificationBroadcast = request.getParameter(NotifyGruConstants.PARAMETER_LEVEL_NOTIFICATION_BROADCAST);

            if (StringUtils.isBlank(strApply)) {
                /*if (nIdMailingListBroadcast == WorkflowUtils.CONSTANT_ID_NULL
                       || strsenderNameBroadcast == WorkflowUtils.EMPTY_STRING
                       || strsubjectBroadcast == WorkflowUtils.EMPTY_STRING
                       || strrecipientsCciBroadcast == WorkflowUtils.EMPTY_STRING
                       || Validator.isRecipientCcValid(strrecipientsCciBroadcast) == false
                       || strrecipientsCcBroadcast == WorkflowUtils.EMPTY_STRING
                       || Validator.isRecipientCcValid(strrecipientsCcBroadcast) == false
                       || strLevelNotificationBroadcast == WorkflowUtils.EMPTY_STRING) {
                    strError = NotifyGruConstants.MESSAGE_MANDATORY_FIELD;
                }*/
            	if(strsenderNameBroadcast == WorkflowUtils.EMPTY_STRING)
            	{
            		_errors.add(NotifyGruConstants.MESSAGE_LIST_SENDER_NAME_FIELD);
            	}
            	if(strsubjectBroadcast == WorkflowUtils.EMPTY_STRING)
            	{
            		_errors.add(NotifyGruConstants.MESSAGE_LIST_SUBJECT_FIELD);
            	}
            	if(strmessageBroadcast == WorkflowUtils.EMPTY_STRING)
            	{
            		_errors.add(NotifyGruConstants.MESSAGE_LIST_MESSAGE_FIELD);
            	}
            }
            if (_errors.size()!=0) 
            {
            	return this.displayErrorMessage(_errors, request);
            }
           /* if (!strError.equals(WorkflowUtils.EMPTY_STRING)) {
                Object[] tabRequiredFields = {I18nService.getLocalizedString(strError, locale)};

                return AdminMessageService.getMessageUrl(request, NotifyGruConstants.MESSAGE_MANDATORY_FIELD,
                        tabRequiredFields, AdminMessage.TYPE_STOP);
            }*/

            /* fin liste diffusion*/
            config.setIdMailingListBroadcast(nIdMailingListBroadcast);
            config.setSenderNameBroadcast(strsenderNameBroadcast);
            config.setSubjectBroadcast(strsubjectBroadcast);
            config.setMessageBroadcast(strmessageBroadcast);
           // config.setRecipientsCcBroadcast(strrecipientsCcBroadcast);
            //config.setRecipientsCciBroadcast(strrecipientsCciBroadcast);
            config.setLevelNotificationBroadcast(strLevelNotificationBroadcast);
            config.setActiveOngletBroadcast(bActiveOngletBROADCAST);
        }

     if (bActiveOngletAgent || bActiveOngletBROADCAST 
             || bActiveOngletEmail || bActiveOngletGuichet 
             || bActiveOngletSMS 
             || (strApply!=null && strApply.equals(NotifyGruConstants.PARAMETER_BUTTON_REMOVE))
             || strProvider!=null) {
            Boolean bCreate = false;
            if (config.getIdTask() == 0) {             
                config.setIdTask(task.getId());
                bCreate = true;
            }

            if (bCreate) {
                _taskNotifyGruConfigService.create(config);
            } else {
                _taskNotifyGruConfigService.update(config);
            }

        } 

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm(HttpServletRequest request, Locale locale, ITask task) {
        TaskNotifyGruConfig config = _taskNotifyGruConfigService.findByPrimaryKey(task.getId());

        String strDefaultSenderName = AppPropertiesService.getProperty(NotifyGruConstants.PROPERTY_NOTIFY_MAIL_DEFAULT_SENDER_NAME);
        Plugin pluginWorkflow = PluginService.getPlugin(WorkflowPlugin.PLUGIN_NAME);
        
        _mokeProviderService=SpringContextService.getBean("workflow-notifygru.mooc1");
        
        Map<String, Object> model = new HashMap<String, Object>();

        model.put(NotifyGruConstants.MARK_CONFIG, config);
        model.put(NotifyGruConstants.MARK_DEFAULT_SENDER_NAME, strDefaultSenderName);
        model.put(NotifyGruConstants.MARK_SELECT_PROVIDER, this.getListProvider());

        ReferenceList listeOnglet = this.getListOnglet(config);
        model.put(NotifyGruConstants.MARK_LIST_ONGLET, listeOnglet);

        ReferenceList levelNotification = this.getListNotification();
        model.put(NotifyGruConstants.MARK_LEVEL_NOTIFICATION_GUICHET, levelNotification);
        model.put(NotifyGruConstants.MARK_LEVEL_NOTIFICATION_AGENT, levelNotification);
        model.put(NotifyGruConstants.MARK_LEVEL_NOTIFICATION_EMAIL, levelNotification);
        model.put(NotifyGruConstants.MARK_LEVEL_NOTIFICATION_SMS, levelNotification);
        model.put(NotifyGruConstants.MARK_LEVEL_NOTIFICATION_BROADCAST, levelNotification);
        model.put(NotifyGruConstants.MARK_INFOS_HELP, _mokeProviderService.getInfosHelp(request, model));
        
      
        model.put(NotifyGruConstants.MARK_MAILING_LIST, _notifyGRUService.getMailingList(request));

        model.put(NotifyGruConstants.MARK_LOCALE, request.getLocale());
        model.put(NotifyGruConstants.MARK_WEBAPP_URL, AppPathService.getBaseUrl(request));
        HtmlTemplate template = AppTemplateService.getTemplate(TEMPLATE_TASK_NOTIFY_GRU_CONFIG, locale, model);
        
        return template.getHtml(  ) ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation(int nIdHistory, HttpServletRequest request, Locale locale, ITask task) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTaskInformationXml(int nIdHistory, HttpServletRequest request, Locale locale, ITask task) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * @return
     */
     public List<AbstractServiceProvider> getImplementationServices(  )
    {   
        return SpringContextService.getBeansOfType( AbstractServiceProvider.class );
    }
     /**
      * 
      * @return
      */
    public ReferenceList getListProvider() {
          ReferenceList refenreceList = new ReferenceList();
        for ( AbstractServiceProvider provider : this.getImplementationServices(  ) )
        {
          
             refenreceList.addItem(provider.getKey(), provider.getTitle(Locale.getDefault()));
           
        }
        return refenreceList;
    }

    /**
     * 
     * @param config
     * @return
     */
    public ReferenceList getListOnglet(TaskNotifyGruConfig config) {

        ReferenceList refenreceList = new ReferenceList();
        refenreceList.addItem(NotifyGruConstants.MARK_ONGLET_GUICHET, NotifyGruConstants.VIEW_GUICHET+" ("+this.getOngletState(config.isActiveOngletGuichet())+" )");
        refenreceList.addItem(NotifyGruConstants.MARK_ONGLET_AGENT, NotifyGruConstants.VIEW_AGENT+" ("+this.getOngletState(config.isActiveOngletAgent())+" )");
        refenreceList.addItem(NotifyGruConstants.MARK_ONGLET_EMAIL, NotifyGruConstants.VIEW_EMAIL+" ("+this.getOngletState(config.isActiveOngletEmail())+" )");
        refenreceList.addItem(NotifyGruConstants.MARK_ONGLET_SMS, NotifyGruConstants.VIEW_SMS+" ("+this.getOngletState(config.isActiveOngletSMS())+" )");
        refenreceList.addItem(NotifyGruConstants.MARK_ONGLET_LIST, NotifyGruConstants.VIEW_BROADCAST_LIST+" ("+this.getOngletState(config.isActiveOngletBroadcast())+" )");

        return refenreceList;
    }
    /**	
     * display the level of notification
     * @return
     */
    public ReferenceList getListNotification() {

        ReferenceList refenreceList = new ReferenceList();
        refenreceList.addItem(0, NotifyGruConstants.VISIBILITY_ALL);
        refenreceList.addItem(1, NotifyGruConstants.VISIBILITY_DOMAIN);
        refenreceList.addItem(2, NotifyGruConstants.VISIBILITY_ADMIN);

        return refenreceList;
    }

   /**
    * display the state of a tab
    * @param strboolean
    * @retur the state of a tab
    */
    public String getOngletState(boolean strboolean)
    {
    		if(strboolean)
    			{
    			return NotifyGruConstants.VIEW_ACTIF;
    			}
    	return NotifyGruConstants.VIEW_INACTIF;
    }

    /**
     * display the error message
     * @param _errors
     * @param request
     * @return the error message
     */
    public String displayErrorMessage(ArrayList<String> _errors, HttpServletRequest request){
    	
          Object[] tabRequiredFields = new Object[_errors.size()];
          for(int i=0;i<_errors.size();i++)
          {
        	  tabRequiredFields[i]=_errors.get(i);
          }
          if(tabRequiredFields.length > 2)
          {
          	return AdminMessageService.getMessageUrl(request,NotifyGruConstants.MESSAGE_MANDATORY_THREE_FIELD,
                      tabRequiredFields, AdminMessage.TYPE_WARNING);
          }
          else if(tabRequiredFields.length == 2)
            {
            	return AdminMessageService.getMessageUrl(request,NotifyGruConstants.MESSAGE_MANDATORY_TWO_FIELD,
                        tabRequiredFields, AdminMessage.TYPE_WARNING);
            }

            return AdminMessageService.getMessageUrl(request,NotifyGruConstants.MESSAGE_MANDATORY_ONE_FIELD,
                    tabRequiredFields, AdminMessage.TYPE_WARNING);
    }
}

