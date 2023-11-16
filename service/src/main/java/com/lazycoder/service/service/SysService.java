package com.lazycoder.service.service;

import com.lazycoder.database.bean.SpringUtil;
import com.lazycoder.service.service.impl.AttachedFileServiceImpl;
import com.lazycoder.service.service.impl.ClassificationServiceImpl;
import com.lazycoder.service.service.impl.CodeLabelServiceImpl;
import com.lazycoder.service.service.impl.DBChangeServiceImpl;
import com.lazycoder.service.service.impl.DatabaseNameServiceImpl;
import com.lazycoder.service.service.impl.FormatInfoServiceImpl;
import com.lazycoder.service.service.impl.FunctionNameDataServiceImpl;
import com.lazycoder.service.service.impl.ImportCodeServicelmpl;
import com.lazycoder.service.service.impl.InputDataSaveServiceImpl;
import com.lazycoder.service.service.impl.ModuleControlServiceImpl;
import com.lazycoder.service.service.impl.ModuleFileFormatServiceImpl;
import com.lazycoder.service.service.impl.ModuleInfoServiceImpl;
import com.lazycoder.service.service.impl.ModuleServiceImpl;
import com.lazycoder.service.service.impl.OptionServiceImpl;
import com.lazycoder.service.service.impl.SysParamServiceImpl;
import com.lazycoder.service.service.impl.SysServiceImpl;
import com.lazycoder.service.service.impl.VariableDataServiceImpl;
import com.lazycoder.service.service.impl.command.AdditionalFunctionServicelmpl;
import com.lazycoder.service.service.impl.command.AdditionalSetServicelmpl;
import com.lazycoder.service.service.impl.command.FunctionServicelmpl;
import com.lazycoder.service.service.impl.command.InitServicelmpl;
import com.lazycoder.service.service.impl.command.MainSetServicelmpl;
import com.lazycoder.service.service.impl.command.ModuleSetServicelmpl;
import com.lazycoder.service.service.impl.format.MainFormatCodeFileServiceImpl;

/**
 * 系统共用服务
 *
 * @author admin
 */
public class SysService {

	public final static ClassificationServiceImpl CLASSIFICATION_SERVICE = (ClassificationServiceImpl) SpringUtil
			.getBean(ClassificationServiceImpl.class);

	public final static ImportCodeServicelmpl IMPORT_CODE_SERVICE = (ImportCodeServicelmpl) SpringUtil
			.getBean(ImportCodeServicelmpl.class);

	public final static ModuleInfoServiceImpl MODULE_INFO_SERVICE = (ModuleInfoServiceImpl) SpringUtil
			.getBean(ModuleInfoServiceImpl.class);

	public final static ModuleServiceImpl MODULE_SERVICE = (ModuleServiceImpl) SpringUtil
			.getBean(ModuleServiceImpl.class);

	public final static FunctionServicelmpl FUNCTION_SERVICE = (FunctionServicelmpl) SpringUtil
			.getBean(FunctionServicelmpl.class);

	public final static InitServicelmpl INIT_SERVICE = (InitServicelmpl) SpringUtil.getBean(InitServicelmpl.class);

	public final static ModuleSetServicelmpl MODULE_SET_SERVICE = (ModuleSetServicelmpl) SpringUtil
			.getBean(ModuleSetServicelmpl.class);

	public final static ModuleFileFormatServiceImpl MODULE_FILE_FORMAT_SERVICE = (ModuleFileFormatServiceImpl) SpringUtil
			.getBean(ModuleFileFormatServiceImpl.class);

	public final static ModuleControlServiceImpl MODULE_CONTROL_SERVICE = (ModuleControlServiceImpl) SpringUtil
			.getBean(ModuleControlServiceImpl.class);

	public final static VariableDataServiceImpl VARIABLE_DATA_SERVICE = (VariableDataServiceImpl) SpringUtil
			.getBean(VariableDataServiceImpl.class);

	public final static MainFormatCodeFileServiceImpl MAIN_FORMAT_CODE_FILE_SERVICE = (MainFormatCodeFileServiceImpl) SpringUtil
			.getBean(MainFormatCodeFileServiceImpl.class);

	public final static com.lazycoder.service.service.impl.format.AdditionalFormatFileServiceImpl ADDITIONAL_FORMAT_FILE_SERVICE = (com.lazycoder.service.service.impl.format.AdditionalFormatFileServiceImpl) SpringUtil
			.getBean(com.lazycoder.service.service.impl.format.AdditionalFormatFileServiceImpl.class);

	public final static OptionServiceImpl OPTION_SERVICE = (OptionServiceImpl) SpringUtil
			.getBean(OptionServiceImpl.class);

	public final static DBChangeServiceImpl DB_CHANGE_SERVICE = (DBChangeServiceImpl) SpringUtil
			.getBean(DBChangeServiceImpl.class);

	public final static FormatInfoServiceImpl FORMAT_INFO_SERVICE = (FormatInfoServiceImpl) SpringUtil
			.getBean(FormatInfoServiceImpl.class);

	public final static MainSetServicelmpl MAIN_SET_SERVICE = (MainSetServicelmpl) SpringUtil
			.getBean(MainSetServicelmpl.class);

	public final static AdditionalSetServicelmpl ADDITIONAL_SET_SERVICE = (AdditionalSetServicelmpl) SpringUtil
			.getBean(AdditionalSetServicelmpl.class);

	public final static AdditionalFunctionServicelmpl ADDITIONAL_FUNCTION_SERVICE = (AdditionalFunctionServicelmpl) SpringUtil
			.getBean(AdditionalFunctionServicelmpl.class);

	public final static SysParamServiceImpl SYS_PARAM_SERVICE = (SysParamServiceImpl) SpringUtil
			.getBean(SysParamServiceImpl.class);

	public final static FunctionNameDataServiceImpl FUNCTION_NAME_DATA_SERVICE = (FunctionNameDataServiceImpl) SpringUtil
			.getBean(FunctionNameDataServiceImpl.class);

	public final static AttachedFileServiceImpl ATTACHED_FILE_SERVICE = (AttachedFileServiceImpl) SpringUtil
			.getBean(AttachedFileServiceImpl.class);

	public final static DatabaseNameServiceImpl DATABASE_NAME_SERVICE = (DatabaseNameServiceImpl) SpringUtil
			.getBean(DatabaseNameServiceImpl.class);

	public final static InputDataSaveServiceImpl INPUT_DATA_SAVE_SERVICE = (InputDataSaveServiceImpl) SpringUtil
			.getBean(InputDataSaveServiceImpl.class);

	public final static CodeLabelServiceImpl CODE_LABEL_SERVICE = (CodeLabelServiceImpl) SpringUtil
			.getBean(CodeLabelServiceImpl.class);

	public final static SysServiceImpl SYS_SERVICE_SERVICE = (SysServiceImpl) SpringUtil
			.getBean(SysServiceImpl.class);

}
