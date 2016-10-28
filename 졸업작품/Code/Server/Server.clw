; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CClientSock
LastTemplate=CAsyncSocket
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "Server.h"

ClassCount=5
Class1=CServerApp
Class2=CServerDlg
Class3=CAboutDlg

ResourceCount=3
Resource1=IDD_ABOUTBOX
Resource2=IDR_MAINFRAME
Class4=CServerSock
Class5=CClientSock
Resource3=IDD_SERVER_DIALOG

[CLS:CServerApp]
Type=0
HeaderFile=Server.h
ImplementationFile=Server.cpp
Filter=N

[CLS:CServerDlg]
Type=0
HeaderFile=ServerDlg.h
ImplementationFile=ServerDlg.cpp
Filter=D
BaseClass=CDialog
VirtualFilter=dWC
LastObject=CServerDlg

[CLS:CAboutDlg]
Type=0
HeaderFile=ServerDlg.h
ImplementationFile=ServerDlg.cpp
Filter=D

[DLG:IDD_ABOUTBOX]
Type=1
Class=CAboutDlg
ControlCount=4
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308480
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889

[DLG:IDD_SERVER_DIALOG]
Type=1
Class=CServerDlg
ControlCount=3
Control1=IDC_LIST_RECEIVE,listbox,1352728835
Control2=IDC_EDIT_SEND,edit,1350631552
Control3=IDC_SEND,button,1342242816

[CLS:CServerSock]
Type=0
HeaderFile=ServerSock.h
ImplementationFile=ServerSock.cpp
BaseClass=CAsyncSocket
Filter=N
VirtualFilter=q
LastObject=CServerSock

[CLS:CClientSock]
Type=0
HeaderFile=ClientSock.h
ImplementationFile=ClientSock.cpp
BaseClass=CAsyncSocket
Filter=N
VirtualFilter=q
LastObject=CClientSock

