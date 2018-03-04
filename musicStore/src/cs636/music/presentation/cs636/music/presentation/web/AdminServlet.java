package cs636.music.presentation.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Invoice;
import cs636.music.service.AdminService;
import cs636.music.service.ServiceException;
import cs636.music.service.UserService;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String ADMIN_JSP_DIR = "/WEB-INF/admin/";
	private AdminService adminService;
	
    @Override
    public void init() throws ServletException
    {
        adminService = MusicSystemConfig.getAdminService();
		this.log("AdminServlet init");
		if (adminService == null)
			System.out.println("Servlet initialization problem!!!");
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        this.log("starting AdminController doPost, uri = " + requestURI);
        String url = "/admin";
        // fix by eoneil: access via /admin only
        // so that (fixed) authorization kicks in at the right spot
        if (requestURI.endsWith("/admin")||requestURI.endsWith("/admin/")) {
            url = ADMIN_JSP_DIR + "adminWelcome.jsp";
        } else if (requestURI.endsWith("/downloads")) {
            url = ADMIN_JSP_DIR + "parameters.jsp";
        } else if (requestURI.endsWith("/displayInvoices")) {
            url = displayInvoices(request, response);
        } else if (requestURI.endsWith("/processInvoice")) {
            url = processInvoice(request, response);
        } else if (requestURI.endsWith("/displayReport")) {
            displayReport(request, response); // creates response: download file
            return;  // can't forward after response sent: let user decide on same page
        } else if (requestURI.endsWith("/displayReports")) {
            url = ADMIN_JSP_DIR + "reports.jsp";
        } else 
            this.log("AdminController doPost can't match "+ requestURI);

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        this.log("starting AdminController doGet, uri = " + requestURI);
        this.log("remote user = " + request.getRemoteUser());
        String url = null;
        if ( requestURI.contains("adminWelcome.html")) {
			url = ADMIN_JSP_DIR + "adminWelcome.jsp";
        /*} else if (requestURI.endsWith("/displayInvoice")) { // Do not have this as an adminService
            url = displayInvoice(request, response);*/
        } else if (requestURI.endsWith("/displayInvoices")) {
            url = displayInvoices(request, response);
         }else 
            this.log("AdminController doGet can't match "+ requestURI);

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
 
    private String displayInvoices(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        Set<InvoiceData> unprocessedInvoices = new HashSet<InvoiceData>();
		String url;
		try {
			unprocessedInvoices = adminService.getListofUnprocessedInvoices();
        } catch (ServiceException e) {
			String error = "Retrieving invoices failed: "
						+ MusicSystemConfig.exceptionReport(e);
				request.setAttribute("error", error);
				url = ADMIN_JSP_DIR + "error.jsp";
				return url;
		}
        
        if (unprocessedInvoices != null) {
            if (unprocessedInvoices.size() <= 0) {
                unprocessedInvoices = null;
            }
        }
        
        // HttpSession session = request.getSession();
        request.setAttribute("unprocessedInvoices", unprocessedInvoices);
        url = ADMIN_JSP_DIR + "invoices.jsp";
        return url;
    }
    
	//Do not have this as an adminService method!
    /*private String displayInvoice(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();

        String invoiceNumberString = request.getParameter("invoiceNumber");
        int invoiceNumber = Integer.parseInt(invoiceNumberString);
        List<Invoice> unprocessedInvoices = (List<Invoice>) 
                session.getAttribute("unprocessedInvoices");

        Invoice invoice = null;
        for (Invoice unprocessedInvoice : unprocessedInvoices) {
            invoice = unprocessedInvoice;
            if (invoice.getInvoiceNumber() == invoiceNumber) {
                break;
            }
        }

        session.setAttribute("invoice", invoice);

        return ADMIN_JSP_DIR + "invoice.jsp";
    }*/

    private String processInvoice(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        Invoice invoice = (Invoice) request.getAttribute("invoice");
		try {
			adminService.processInvoice(invoice.getInvoiceId());
		} catch (ServiceException e) {
			String error = "Processing this invoice failed: "
						+ MusicSystemConfig.exceptionReport(e);
				request.setAttribute("error", error);
				String url = ADMIN_JSP_DIR + "error.jsp";
				return url;
		}
        
        return  "/admin/displayInvoices";
    }
    
    private void displayReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		String reportName = request.getParameter("reportName");
        // String startDate = request.getParameter("startDate");
        // String endDate = request.getParameter("endDate");
        
        Set<DownloadData> downloads = new HashSet<DownloadData> ();
        if (reportName.equalsIgnoreCase("userEmail")) {
            System.out.println("Will show list f registered users.");// workbook = ReportDB.getUserEmail();
        } else if (reportName.equalsIgnoreCase("downloadDetail")) {
            try {
				downloads = adminService.getListofDownloads();
			} catch (ServiceException e) {
				String error = "Retrieving list of downloads failed: "
						+ MusicSystemConfig.exceptionReport(e);
				request.setAttribute("error", error);
			}
			
			/* Now how to display this list...*/
				
		}
	}
                /*response.setHeader("content-disposition", 
                "attachment; filename=" + reportName + ".xls");
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        }
    }*/
}