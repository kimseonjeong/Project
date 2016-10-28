// ClientSock.cpp : implementation file
//

#include "stdafx.h"
#include "Server.h"
#include "ClientSock.h"
#include "ServerDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CClientSock

CClientSock::CClientSock()
{
}

CClientSock::~CClientSock()
{
}


// Do not edit the following lines, which are needed by ClassWizard.
#if 0
BEGIN_MESSAGE_MAP(CClientSock, CAsyncSocket)
	//{{AFX_MSG_MAP(CClientSock)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()
#endif	// 0

/////////////////////////////////////////////////////////////////////////////
// CClientSock member functions

void CClientSock::OnReceive(int nErrorCode) 
{
	// TODO: Add your specialized code here and/or call the base class
	CServerDlg* dlg = (CServerDlg*)AfxGetApp()->m_pMainWnd;
	dlg->OnReceive(this);
	CAsyncSocket::OnReceive(nErrorCode);
}
