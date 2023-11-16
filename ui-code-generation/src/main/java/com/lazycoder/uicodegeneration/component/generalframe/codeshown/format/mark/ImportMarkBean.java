package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.AddCodeTemporaryVariable;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.TheCodeStatement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.UpdateCodeTemporaryVariable;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.BaseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.StringBean;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForMark;
import java.util.ArrayList;
import lombok.Data;

@Data
public class ImportMarkBean extends AbstractMarkBean {

//	private StringBean stringBean;

    public ImportMarkBean(JSONObject elementJSONObject) {
        // TODO Auto-generated constructor stub
        super();
        init();
        setMarkElement(ImportMarkElement.jsonParsing(elementJSONObject));
    }


    public ImportMarkBean() {
        // TODO Auto-generated constructor stub
        super();
    }


//	/**
//	 * 根据 JSONObject 还原
//	 *
//	 * @param markBeanJSONObject
//	 * @return
//	 */
//	public static ImportMarkBean restoreByJSONObject(JSONObject markBeanJSONObject) {
//		ImportMarkBean markBean = JsonUtil.restoreByJSONObject(markBeanJSONObject, ImportMarkBean.class);
//		JSONObject codeListJSONObject = getCodeListJSONObject(markBeanJSONObject),
//				markElementJSONObject = getMarkElementJSONObject(markBeanJSONObject);
//		CodeList theCodeList = CodeList.restoreByJSONObject(codeListJSONObject);
//		markBean.setCodeList(theCodeList);
//		ImportMarkElement markElement = ImportMarkElement.jsonParsing(markElementJSONObject);
//		markBean.setMarkElement(markElement);
//		markBean.stringBean = markBean.getStringBean();
//		return markBean;
//	}


    private void init() {
        setMarkType(MarkElementName.IMPORT_MARK);
        TheCodeStatement thisCode = new TheCodeStatement(0, 1);

        StringBean stringBean = new StringBean();
        stringBean.setGfs(StringBean.FALSE_);
        thisCode.getCodeStatementBeanList().add(stringBean);

        this.getCodeList().getCodeList().add(thisCode);
    }

//	@JSONField(deserialize = false, serialize = false)
//	@Override
//	public String getTheBeanValue() {
//		// TODO Auto-generated method stub
//		return stringBean.getTheBeanValue();
//	}

    private StringBean gainStringBean() {
        StringBean sbean = null;
        ArrayList<TheCodeStatement> theCodeList = this.getCodeList().getCodeList();
        if (theCodeList != null) {
            if (theCodeList.size() > 0) {
                TheCodeStatement thisCode = theCodeList.get(theCodeList.size() - 1);
                ArrayList<BaseBean> codeStatementBeanList = thisCode.getCodeStatementBeanList();
                if (codeStatementBeanList != null) {
                    if (codeStatementBeanList.size() > 0) {
                        BaseBean baseBean = codeStatementBeanList.get(codeStatementBeanList.size() - 1);
                        if (baseBean instanceof StringBean) {
                            sbean = (StringBean) baseBean;
                        }
                    }
                }
            }
        }
        return sbean;
    }

    /**
     * 添加引入代码
     *
     * @param codeContent
     */
    public void addImportCode(String codeContent) {
        StringBean stringBean = gainStringBean();
        if (stringBean != null) {
            if (stringBean.getTheBeanValue().contains(codeContent) == false) {
                StringBuilder out = new StringBuilder(stringBean.getTheBeanValue());
                out.append(codeContent);
                stringBean.updateBeanValue(out.toString() + "\n");
            }
        }
    }

    /**
     * 删除引入代码
     *
     * @param codeContent
     */
    public void delImportCode(String codeContent) {
        StringBean stringBean = gainStringBean();
        if (stringBean != null) {
            if (stringBean.getTheBeanValue().contains(codeContent) == true) {
                String temp = stringBean.getTheBeanValue().replace(codeContent + "\n", "");
                stringBean.updateBeanValue(temp);
            }
        }
    }

    @Override
    public String getThisCodeText() {
        String thisCodeText ="";
        StringBean stringBean = gainStringBean();
        if (stringBean != null) {
            thisCodeText = stringBean.getTheBeanValue();
        }
        return thisCodeText;
    }

//	@JSONField(deserialize = false, serialize = false)
//	@Override
//	public int getValueLength() {
//		// TODO Auto-generated method stub
//		return stringBean.getValueLength();
//	}

    @Override
    public ImportMarkElement getMarkElement() {
        return (ImportMarkElement) markElement;
    }

    @Deprecated
    @Override
    public CodeList getCodeList() {
        return super.getCodeList();
    }

    @Deprecated
    @Override
    public UpdateCodeTemporaryVariable delCode(DelCodeEditParamForMark delCodeEditParamForMark) {
        return super.delCode(delCodeEditParamForMark);
    }

    @Deprecated
    @Override
    public UpdateCodeTemporaryVariable updateValue(UpdateCodeEditParamForMark updateCodeEditParamForMark) {
        return super.updateValue(updateCodeEditParamForMark);
    }

    @Deprecated
    @Override
    public boolean checkWhetherItMatchesForMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        return super.checkWhetherItMatchesForMark(addCodeEditParamForMark);
    }

    @Deprecated
    @Override
    public AddCodeTemporaryVariable addCode(AddCodeEditParamForMark addCodeEditParamForMark) {
        return super.addCode(addCodeEditParamForMark);
    }

    @Deprecated
    @Override
    public int addCode(int codeSerialNumber, int codeOrdinal, String codeStatement, boolean addToLast, Integer nextCodeSerialNumber, boolean inserNewLineOrNot) {
        return super.addCode(codeSerialNumber, codeOrdinal, codeStatement, addToLast, nextCodeSerialNumber, inserNewLineOrNot);
    }

    @Deprecated
    @Override
    public int addCode(PathFind pathFind, int codeSerialNumber, int codeOrdinal, String codeLabelId, String codeStatement, boolean addToLast, Integer nextCodeSerialNumber, boolean inserNewLineOrNot) {
        return super.addCode(pathFind, codeSerialNumber, codeOrdinal, codeLabelId, codeStatement, addToLast, nextCodeSerialNumber, inserNewLineOrNot);
    }
}
