// ServerDlg.h : header file
//
#include "ServerSock.h"
#include "ClientSock.h"

#if !defined(AFX_SERVERDLG_H__98A042E1_27EE_4CCC_AF3A_9E6AE372C409__INCLUDED_)
#define AFX_SERVERDLG_H__98A042E1_27EE_4CCC_AF3A_9E6AE372C409__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CServerDlg dialog

class CServerDlg : public CDialog
{
// Construction
public:
	void Send(char* buf);
	void OnReceive(CClientSock* pSock);
	void OnAccept();
	CServerSock* m_pServerSock;
	CTypedPtrList<CObList, CClientSock*> m_clientSocks;
	CServerDlg(CWnd* pParent = NULL);	// standard constructor
	~CServerDlg();

// Dialog Data
	//{{AFX_DATA(CServerDlg)
	enum { IDD = IDD_SERVER_DIALOG };
	CListBox	m_listReceive;
	CString	m_strSend;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CServerDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CServerDlg)
	virtual BOOL OnInitDialog();
//	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
//	afx_msg void OnPaint();
//	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnSend();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnPaint();
	afx_msg void OnEnChangeEditSend();
	afx_msg void OnBnClickedSend();
	afx_msg void OnLbnSelchangeListReceive();
	afx_msg void OnIpnFieldchangedIpaddress1(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnBnClickedCancle();
	afx_msg void OnBnClickedButton1();
	afx_msg void OnBnClickedButton2();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SERVERDLG_H__98A042E1_27EE_4CCC_AF3A_9E6AE372C409__INCLUDED_)
