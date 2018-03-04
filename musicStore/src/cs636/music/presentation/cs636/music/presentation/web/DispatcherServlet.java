package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.UserService;

// like Spring MVC DispatcherServlet and its config file, but simpler.
// This servlet is handling the student pages of the pizza project,
// calling on various controllers, roughly one for each student page or form.
// Note that all the jsp filenames (for views) are in this file, not
// in the controllers themselves.  Each controller is set up
// here and given its forward-to URLs in its constructor.

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 3971217231726348088L;
	private static UserService userService;
	private static int numRooms; // number of rooms in system;

	// The controllers, roughly one per student page or form
	private static Controller userWelcomeController;
	private static Controller orderReceiveController;
	private static Controller orderFormController;
	private static Controller orderPizzaController;
		
	private static final String SITE_WELCOME_URL = "/welcome.html";
	private static final String SITE_WELCOME_VIEW = "/welcome.jsp";
	private static final String USER_WELCOME_URL = "/userWelcome.html";
	private static final String USER_WELCOME_VIEW = "/WEB-INF/jsp/userWelcome.jsp";
	private static final String CATALOG_URL = "/catalog.html";
	private static final String CATALOG_VIEW = "/WEB-INF/jsp/catalog.jsp";
	private static final String CART_URL = "/cart.html";
	private static final String CART_VIEW = "/WEB-INF/jsp/cart.jsp";
	private static final String PRODUCT_URL = "/products.html";
	private static final String PRODUCT_VIEW = "/WEB-INF/jsp/products.jsp";
	private static final String INVOICE_URL = "/invoice.html";
	private static final String INVOICE_VIEW = "/WEB-INF/jsp/invoice.jsp";
	private static final String SOUND_URL = "/sound.html";
	private static final String SOUND_VIEW = "/WEB-INF/jsp/sound.jsp";
	private static final String USERREG_URL = "/userreg.html";
	private static final String USERREG_VIEW = "/WEB-INF/jsp/userreg.jsp";
	private static final String CHECKEDOUT_URL = "/thankyou.html";
	private static final String CHECKEDOUT_VIEW = "/WEB-INF/jsp/thankyou.jsp";
	// the order form submits to the following URL
	//private static final String ORDER_PIZZA_URL = "/orderPizza.html"; - invoice above!
	// a successful pizza order is followed by a redirect to the this URL: 
	private static final String CHECKEDOUT_REDIRECT_URL = "/userWelcome.html";
	
	// Initialization of servlet: runs before any request is
	// handled in the web app. It does PizzaSystemConfig initialization
	// then sets up all the controllers
	@Override
	public void init() throws ServletException {
		System.out.println("Starting dispatcher servlet initialization");
		try {
			MusicSystemConfig.configureServices();
		} catch (Exception e) {
			// log the error in tomcat's log
			System.out.println(MusicSystemConfig.exceptionReport(e));
			throw new ServletException(e); // fatal error
		}
		userService = MusicSystemConfig.getUserService();
		if (userService==null)
			throw new ServletException(
					"DispatcherServlet: bad initialization");
		numRooms = MusicSystemConfig.NUM_OF_ROOMS;
		// create all the controllers and their forward URLs
		studentWelcomeController = new StudentWelcomeController(studentService,
				STUDENT_WELCOME_VIEW);
		orderReceiveController = new OrderReceiveController(studentService, 
				STUDENT_WELCOME_URL);
		orderFormController = new OrderFormController(studentService,
				numRooms, ORDER_FORM_VIEW);
		// validating form controller: two URLs can be next-- 
		// success or redoing of form via its controller
		orderMusicController = new OrderPizzaController(studentService,
				STUDENT_WELCOME_URL, ORDER_FORM_URL);
	}
	
	// Called when app server is shutting this servlet down
	// because it is shutting the app down.
	// Since this servlet is in charge of this app, it is
	// the one to respond by shutting down the BL+DAO
	// (the SysTestServlet ignores the shutdown)
	@Override
	public void destroy() {
		System.out.println("DispatcherServlet: shutting down");
		try {
			MusicSystemConfig.shutdownServices();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get webapp-specific part of the URL, the part after
		// the context path that identifies the webapp
		String requestURL = request.getServletPath();
		System.out.println("DispatcherServlet: requestURL = " + requestURL);
		// At userWelcome, the user gets a userBean.
		// If it's not there for subsequent pages, hand the request to
		// userWelcome. Having the bean is like being "logged in".
		boolean hasBean = (request.getSession().getAttribute("user")
				!= null);

		// Dispatch to the appropriate Controller, which will determine
		// the next URL to use as well as do its own actions.
		// The URL returned by handleRequest will be a context-relative URL, 
		// like /WEB-INF/jsp/foo.jsp (a view) 
		// or /something.html (for a controller).
		// Note that although resources below /WEB-INF are inaccessible
		// to direct HTTP requests, they are accessible to forwards
		String forwardURL = null; 
		if (requestURL.equals(SITE_WELCOME_URL))
			forwardURL = SITE_WELCOME_VIEW; // no controller needed
        // test for bean, and if not there, send user to student welcome page
		else if (requestURL.equals(STUDENT_WELCOME_URL) || !hasBean)  
			forwardURL = userWelcomeController.handleRequest(request, response);
		else if (requestURL.equals(ORDER_RECEIVE_URL))
			forwardURL = orderReceiveController.handleRequest(request, response);
		else if (requestURL.equals(ORDER_FORM_URL))
			forwardURL = orderFormController.handleRequest(request, response);
		else if (requestURL.equals(ORDER_PIZZA_URL)) {
			String returnedURL = orderMusicController.handleRequest(request, response);
			// Here returned URL = ORDER_SUCCESS_VIEW (success case)
			// or ORDER_FORM_URL (redo form case)			
			if (returnedURL.equals(ORDER_FORM_URL))
				forwardURL = returnedURL;  // handle normally (redo form case)
			else if (returnedURL.equals(STUDENT_WELCOME_URL)) { // success case
				// Special case: we avoid accidental resubmissions of the 
				// order form by redirecting rather than forwarding.
				// This way, a user page-refresh in the browser just redisplays
				// the "success" page
				response.sendRedirect(request.getContextPath() + ORDER_REDIRECT_URL);
				return; // we sent a redirect instead of forwarding
			}  else throw new ServletException(
					"DispatcherServlet: Unknown url back from orderPizzaController: "
							+ returnedURL);
		// the redirect comes back here--forward to success page
		} else if (requestURL.equals(ORDER_REDIRECT_URL)) 
			forwardURL = STUDENT_WELCOME_URL; // the order success page
		else 
			throw new ServletException("DispatcherServlet: Unknown servlet path: "
					+ requestURL);
		// Here with good forwardURL to forward to
		System.out.println("DispatcherServlet: forwarding to "+ forwardURL);
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(forwardURL);
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
