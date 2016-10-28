#if !defined(AFX_SERVERSOCK_H__9490DDC2_F84A_4654_B1A8_509AA2991222__INCLUDED_)
#define AFX_SERVERSOCK_H__9490DDC2_F84A_4654_B1A8_509AA2991222__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ServerSock.h : header file
//



/////////////////////////////////////////////////////////////////////////////
// CServerSock command target

class CServerSock : public CAsyncSocket
{
// Attributes
public:

// Operations
public:
	CServerSock();
	virtual ~CServerSock();

// Overrides
public:
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CServerSock)
	public:
	virtual void OnAccept(int nErrorCode);
	//}}AFX_VIRTUAL

	// Generated message map functions
	//{{AFX_MSG(CServerSock)
		// NOTE - the ClassWizard will add and remove member functions here.
	//}}AFX_MSG

// Implementation
protected:
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SERVERSOCK_H__9490DDC2_F84A_4654_B1A8_509AA2991222__INCLUDED_)
