package fr.paris.lutece.plugins.workflow.modules.notifygru.utils.constants;


/**
 *
 * NotifyGruConstants
 *
 */
public final class NotifyGruConstants
{
    // CONSTANTS
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSED_BRACKET = ")";
    public static final String HYPHEN = "-";
    public static final String SLASH = "/";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USER_AUTO = "auto";
    public static final String TASK_NOTIFY_GRU_KEY = "taskNotifyGru";

    // FIELDS
    public static final String FIELD_NOTIFY = "module.workflow.notifygru.task_notify_directory_config.label_notify_by";
    public static final String FIELD_SUBJECT = "module.workflow.notifygru.task_notify_directory_config.label_subject";
    public static final String FIELD_MESSAGE = "module.workflow.notifygru.task_notify_directory_config.label_message";
    public static final String FIELD_SENDER_NAME = "module.workflow.notifygru.task_notify_directory_config.label_sender_name";
    public static final String FIELD_TASK_DIRECTORY = "module.workflow.notifygru.task_notify_directory_config.label_task_directory";
    public static final String FIELD_TASK_ENTRY_GRU_SMS = "module.workflow.notifygru.task_notify_directory_config.label_task_entry_directory_sms";
    public static final String FIELD_TASK_ENTRY_GRU_EMAIL = "module.workflow.notifygru.task_notify_directory_config.label_task_entry_directory_email";
    public static final String FIELD_TASK_ENTRY_GRU_USER_GUID = "module.workflow.notifygru.task_notify_directory_config.label_task_entry_directory_user_guid";
    public static final String FIELD_MAILING_LIST = "module.workflow.notifygru.task_notify_directory_config.label_mailing_list";
    public static final String FIELD_STATE = "module.workflow.notifygru.task_notify_directory_config.label_state";
    public static final String FIELD_MESSAGE_VALIDATION = "module.workflow.notifygru.task_notify_directory_config.label_message_validation";
    public static final String FIELD_LABEL_LINK = "module.workflow.notifygru.task_notify_directory_config.label_label_link";
    public static final String FIELD_LABEL_LINK_VIEW_RECORD = "module.workflow.notifygru.task_notify_directory_config.label_label_link_view_record";
    public static final String FIELD_LABEL_PERIOD_VALIDITY = "module.workflow.notifygru.task_notify_directory_config.label_period_validity";

    // MESSAGES
    public static final String MESSAGE_MANDATORY_FIELD = "module.workflow.notifygru.message.mandatory.field";
    public static final String MESSAGE_EQUAL_FIELD = "module.workflow.notifygru.message.equal.field";
    public static final String MESSAGE_ERROR_VALIDATION = "module.workflow.notifygru.message.error_validation";

    // PROPERTIES
    public static final String PROPERTY_ACCEPTED_GRU_ENTRY_TYPE_EMAIL_SMS = "workflow-notifygru.acceptedDirectoryEntryTypesEmailSMS";
    public static final String PROPERTY_ACCEPTED_GRU_ENTRY_TYPE_USER_GUID = "workflow-notifygru.acceptedDirectoryEntryTypesUserGuid";
    public static final String PROPERTY_REFUSED_GRU_ENTRY_TYPE_USER_GUID = "workflow-notifygru.refusedDirectoryEntryTypes";
    public static final String PROPERTY_ACCEPTED_GRU_ENTRY_TYPE_FILE = "workflow-notifygru.acceptedDirectoryEntryTypesFile";
    public static final String PROPERTY_NOTIFY_MAIL_DEFAULT_SENDER_NAME = "workflow-notifygru.notification_mail.default_sender_name";
    public static final String PROPERTY_SERVER_SMS = "workflow-notifygru.email_server_sms";
    public static final String PROPERTY_XPAGE_PAGETITLE = "module.workflow.notifygru.xpage.pagetitle";
    public static final String PROPERTY_XPAGE_PATHLABEL = "module.workflow.notifygru.xpage.pathlabel";
    public static final String PROPERTY_LUTECE_ADMIN_PROD_URL = "lutece.admin.prod.url";
    public static final String PROPERTY_LUTECE_BASE_URL = "lutece.base.url";
    public static final String PROPERTY_LUTECE_PROD_URL = "lutece.prod.url";

    // MARKS GUICHET
    public static final String MARK_LIST_ID_RESOURCE_GUICHET = "list_id_resource_guichet";
    public static final String MARK_ID_RESOURCE_GUICHET = "id_resource_guichet";
    public static final String MARK_LIST_ID_DEMAND_GUICHET = "list_id_demand_guichet";
    public static final String MARK_ID_DEMAND_GUICHET = "id_demand_guichet";
    public static final String MARK_LIST_USER_GUID_GUICHET = "list_user_guid_guichet";
    public static final String MARK_USER_GUID_GUICHET = "user_guid_guichet";
    public static final String MARK_LIST_CRM_WEBAPP_CODE_GUICHET = "list_crm_web_app_code_guichet";
    public static final String MARK_CRM_WEBAPP_CODE_GUICHET = "crm_web_app_code_guichet";
    public static final String MARK_SEND_NOTIFICATION_GUICHET = "send_notification_guichet";
    public static final String MARK_STATUS_TEXT_GUICHET = "status_text_guichet";
    public static final String MARK_SUBJECT_GUICHET = "subject_guichet";
    public static final String MARK_MESSAGE_GUICHET = "message_guichet";
    public static final String MARK_SENDER_NAME_GUICHET = "sender_name_guichet";
    public static final String MARK_LEVEL_NOTIFICATION_GUICHET = "level_notification_guichet";
    public static final String MARK_IS_ACTIVE_ONGLET_GUICHET = "is_active_onglet_guichet";
    // MARKS EMAIL
    public static final String MARK_LIST_ID_RESOURCE_EMAIL = "list_id_resource_email";
    public static final String MARK_ID_RESOURCE_EMAIL = "id_resource_email";
    public static final String MARK_LIST_ID_DEMAND_EMAIL = "list_id_record_email";
    public static final String MARK_ID_DEMAND_EMAIL = "id_record_email";
    public static final String MARK_LIST_USER_GUID_EMAIL = "list_record_user_guid_email";
    public static final String MARK_USER_GUID_EMAIL = "record_user_guid_email";
    public static final String MARK_IS_NOTIFIED_BY_EMAIL = "is_notified_by_email";
    public static final String MARK_SUBJECT_EMAIL = "subject_email";
    public static final String MARK_ENTRY_EMAIL = "entry_email";
    public static final String MARK_MESSAGE_EMAIL = "message_email";
    public static final String MARK_SENDER_NAME_EMAIL = "sender_name_email";
    public static final String MARK_RECIPIENT_EMAIL = "recipient_email";
    public static final String MARK_RECIPIENT_CC_EMAIL = "recipient_cc_email";
    public static final String MARK_RECIPIENT_CCI_EMAIL = "recipient_cci_email";
    public static final String MARK_LEVEL_NOTIFICATION_EMAIL = "level_notification_email";
    public static final String MARK_IS_ACTIVE_ONGLET_EMAIL = "is_active_onglet_email";
    // MARKS SMS
    public static final String MARK_LIST_ID_RESOURCE_SMS = "list_id_resource_sms";
    public static final String MARK_ID_RESOURCE_SMS = "id_resource_sms";
    public static final String MARK_LIST_ID_DEMAND_SMS = "list_id_record_sms";
    public static final String MARK_ID_DEMAND_SMS = "id_record_sms";
    public static final String MARK_LIST_USER_GUID_SMS = "list_record_user_guid_sms";
    public static final String MARK_USER_GUID_SMS = "record_user_guid_sms";
    public static final String MARK_IS_NOTIFIED_BY_SMS = "is_notified_by_sms";
    public static final String MARK_SUBJECT_SMS = "subject_sms";
    public static final String MARK_ENTRY_SMS = "entry_sms";
    public static final String MARK_MESSAGE_SMS = "message_sms";
    public static final String MARK_SENDER_NAME_SMS = "sender_name_sms";
    public static final String MARK_RECIPIENT_SMS = "recipient_sms";
    public static final String MARK_RECIPIENT_CC_SMS = "recipient_cc_sms";
    public static final String MARK_RECIPIENT_CCI_SMS = "recipient_cci_sms";
    public static final String MARK_LEVEL_NOTIFICATION_SMS = "level_notification_sms";
    public static final String MARK_IS_ACTIVE_ONGLET_SMS = "is_active_onglet_sms";
    
 // PARAMETERS GUICHET
    public static final String PARAMETER_ID_RESOURCE_GUICHET = "id_resource_guichet";
    public static final String PARAMETER_ID_DEMAND_GUICHET = "id_demand_guichet";
    public static final String PARAMETER_USER_GUID_GUICHET = "user_guid_guichet";
    public static final String PARAMETER_CRM_WEBAPP_CODE_GUICHET = "crm_web_app_code_guichet";
    public static final String PARAMETER_SEND_NOTIFICATION_GUICHET = "send_notification_guichet";
    public static final String PARAMETER_STATUS_TEXT_GUICHET = "status_text_guichet";
    public static final String PARAMETER_SUBJECT_GUICHET = "subject_guichet";
    public static final String PARAMETER_MESSAGE_GUICHET = "message_guichet";
    public static final String PARAMETER_SENDER_NAME_GUICHET = "sender_name_guichet";
    public static final String PARAMETER_LEVEL_NOTIFICATION_GUICHET = "level_notification_guichet";
    public static final String PARAMETER_IS_ACTIVE_ONGLET_GUICHET = "is_active_onglet_guichet";
    // PARAMETERS EMAIL
    public static final String PARAMETER_ID_RESOURCE_EMAIL = "id_resource_email";
    public static final String PARAMETER_ID_DEMAND_EMAIL = "id_record_email";
    public static final String PARAMETER_USER_GUID_EMAIL = "record_user_guid_email";
    public static final String PARAMETER_IS_NOTIFIED_BY_EMAIL = "is_notified_by_email";
    public static final String PARAMETER_SUBJECT_EMAIL = "subject_email";
    public static final String PARAMETER_ENTRY_EMAIL = "entry_email";
    public static final String PARAMETER_MESSAGE_EMAIL = "message_email";
    public static final String PARAMETER_SENDER_NAME_EMAIL = "sender_name_email";
    public static final String PARAMETER_RECIPIENT_EMAIL = "recipient_email";
    public static final String PARAMETER_RECIPIENT_CC_EMAIL = "recipient_cc_email";
    public static final String PARAMETER_RECIPIENT_CCI_EMAIL = "recipient_cci_email";
    public static final String PARAMETER_LEVEL_NOTIFICATION_EMAIL = "level_notification_email";
    public static final String PARAMETER_IS_ACTIVE_ONGLET_EMAIL = "is_active_onglet_email";
    // PARAMETERS SMS
    public static final String PARAMETER_ID_RESOURCE_SMS = "id_resource_sms";
    public static final String PARAMETER_ID_DEMAND_SMS = "id_record_sms";
    public static final String PARAMETER_USER_GUID_SMS = "record_user_guid_sms";
    public static final String PARAMETER_IS_NOTIFIED_BY_SMS = "is_notified_by_sms";
    public static final String PARAMETER_SUBJECT_SMS = "subject_sms";
    public static final String PARAMETER_ENTRY_SMS = "entry_sms";
    public static final String PARAMETER_MESSAGE_SMS = "message_sms";
    public static final String PARAMETER_SENDER_NAME_SMS = "sender_name_sms";
    public static final String PARAMETER_RECIPIENT_SMS = "recipient_sms";
    public static final String PARAMETER_RECIPIENT_CC_SMS = "recipient_cc_sms";
    public static final String PARAMETER_RECIPIENT_CCI_SMS = "recipient_cci_sms";
    public static final String PARAMETER_LEVEL_NOTIFICATION_SMS = "level_notification_sms";
    public static final String PARAMETER_IS_ACTIVE_ONGLET_SMS = "is_active_onglet_sms";
    
    
    
    // AUTHER MARKS
    public static final String MARK_POSITION = "position_";
    public static final String MARK_GRU_TITLE = "directory_title";
    public static final String MARK_GRU_DESCRIPTION = "directory_description";
    public static final String MARK_LINK = "link";
    public static final String MARK_DEFAULT_SENDER_NAME = "default_sender_name";
    public static final String MARK_LIST_ENTRIES_EMAIL_SMS = "list_entries_email_sms";
    public static final String MARK_GRU_LIST = "list_directory";
    public static final String MARK_CONFIG = "config";
    public static final String MARK_STATE_LIST = "list_state";
    public static final String MARK_LIST_ENTRIES_FREEMARKER = "list_entries_freemarker";
    public static final String MARK_WEBAPP_URL = "webapp_url";
    public static final String MARK_LOCALE = "locale";
    public static final String MARK_IS_USER_ATTRIBUTE_WS_ACTIVE = "is_user_attribute_ws_active";
    public static final String MARK_LIST_ENTRIES_USER_GUID = "list_entries_user_guid";
    public static final String MARK_MESSAGE_VALIDATION = "message_validation_success";
    public static final String MARK_MAILING_LIST = "mailing_list";
    public static final String MARK_PLUGIN_WORKFLOW = "plugin_workflow";
    public static final String MARK_TASKS_LIST = "tasks_list";
    public static final String MARK_TASK = "task_";
    public static final String MARK_FIRST_NAME = "first_name";
    public static final String MARK_LAST_NAME = "last_name";
    public static final String MARK_EMAIL = "email";
    public static final String MARK_PHONE_NUMBER = "phone_number";
    public static final String MARK_LINK_VIEW_RECORD = "link_view_record";
    public static final String MARK_LIST_POSITION_ENTRY_FILE_CHECKED = "list_position_entry_file_checked";
    public static final String MARK_LIST_ENTRIES_FILE = "list_entries_file";
    
    // PARAMETERS
    public static final String PARAMETER_PAGE = "page";
    public static final String PARAMETER_KEY = "key";
    public static final String PARAMETER_APPLY = "apply";
    public static final String PARAMETER_NOTIFY = "notify";
    // PARAMETERS GUICHET
    public static final String PARAMETER_SUBJECT_ONGLE1 = "subject_ongle1";
    public static final String PARAMETER_MESSAGE_ONGLE1 = "message_ongle1";
    public static final String PARAMETER_SENDER_NAME_ONGLE1 = "sender_name_ongle1";
    public static final String PARAMETER_SUBJECT_ONGLE3 = "subject_ongle3";
    public static final String PARAMETER_MESSAGE_ONGLE3 = "message_ongle3";
    public static final String PARAMETER_SENDER_NAME_ONGLE3 = "sender_name_ongle3";
    
    public static final String PARAMETER_ID_DIRECTORY_ONGLE1 = "id_directory_ongle1";
    public static final String PARAMETER_ID_DIRECTORY_ONGLE3 = "id_directory_ongle1";
    public static final String PARAMETER_POSITION_ENTRY_GRU_SMS = "position_entry_directory_sms";
    public static final String PARAMETER_POSITION_ENTRY_GRU_EMAIL = "position_entry_directory_email";
    public static final String PARAMETER_POSITION_ENTRY_GRU_USER_GUID_ONGLE1 = "position_entry_directory_user_guid_ongle1";
    public static final String PARAMETER_POSITION_ENTRY_GRU_USER_GUID_ONGLE3 = "position_entry_directory_user_guid_ongle3";
    public static final String PARAMETER_ID_STATE = "id_state";
    public static final String PARAMETER_EMAIL_VALIDATION = "email_validation";
    public static final String PARAMETER_MESSAGE_VALIDATION = "message_validation";
    public static final String PARAMETER_LABEL_LINK = "label_link";
    public static final String PARAMETER_PERIOD_VALIDTY = "period_validity";
    public static final String PARAMETER_IS_NOTIFY_BY_USER_GUID = "is_notify_by_user_guid";
    public static final String PARAMETER_RECIPIENTS_CC = "recipients_cc";
    public static final String PARAMETER_RECIPIENTS_BCC = "recipients_bcc";
    public static final String PARAMETER_ID_MAILING_LIST = "id_mailing_list";
    public static final String PARAMETER_VIEW_RECORD = "view_record";
    public static final String PARAMETER_LABEL_LINK_VIEW_RECORD = "label_link_view_record";
    public static final String PARAMETER_LIST_POSITION_ENTRY_FILE_CHECKED = "list_position_entry_file_checked";

    // TAGS
    public static final String TAG_A = "a";

    // ATTRIBUTES
    public static final String ATTRIBUTE_HREF = "href";

    // JSP
    public static final String JSP_DO_VISUALISATION_RECORD = "jsp/admin/plugins/directory/DoVisualisationRecord.jsp";

    /**
     * Private constructor
     */
    private NotifyGruConstants(  )
    {
    }
}
