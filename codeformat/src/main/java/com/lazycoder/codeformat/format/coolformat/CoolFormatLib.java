package com.lazycoder.codeformat.format.coolformat;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface CoolFormatLib extends Library{

	CoolFormatLib INSTANCE_DLL = (CoolFormatLib) Native.load("CoolFormatLib", CoolFormatLib.class);

	boolean DoFormatter(long nLanguage, Pointer pszTextIn, Pointer pszTextOut, IntByReference nTextOut,
						Pointer pszMsgOut, IntByReference nMsgOut, long uCodepage, Pointer pszEol, Pointer pszInitIndent);

	void ShowSettings();
}
