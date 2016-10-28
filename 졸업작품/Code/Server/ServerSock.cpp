// ServerSock.cpp : implementation file
//

#include "stdafx.h"
#include "Server.h"
#include "ServerSock.h"
#include "ServerDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CServerSock

CServerSock::CServerSock()
{
}

CServerSock::~CServerSock()
{
}


// Do not edit the following lines, which are needed by ClassWizard.
#if 0
BEGIN_MESSAGE_MAP(CServerSock, CAsyncSocket)
	//{{AFX_MSG_MAP(CServerSock)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()
#endif	// 0

/////////////////////////////////////////////////////////////////////////////
// CServerSock member functions

void CServerSock::OnAccept(int nErrorCode) 
{
	// TODO: Add your specialized code here and/or call the base class
	CServerDlg* dlg = (CServerDlg*)AfxGetApp()->m_pMainWnd;
	dlg->OnAccept();
	CAsyncSocket::OnAccept(nErrorCode);
}
