package cs636.music.presentation.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.PizzaOrderData;
import cs636.music.service.userService;
import cs636.music.domain.User;

public class UserWelcomeController implements Controller {

	private String view;
	private UserService userService;

	public UserWelcomeController(StudentService studentService, String view) {
		System.out
				.println("setting userService in UserWelcomeController (isnull = "
						+ (userService == null));
		this.userService = userService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		System.out.println("in UserWelcomeController (isnull = "
				+ (studentService == null));
		HttpSession session = request.getSession();
		//Integer roomNo = null;
		// take room parameter over session var in StudentBean--
		/*User user = (User) request.getParameter("user");
		if (user != null) {
			try {
				roomNo = Integer.parseInt(paramRoomNoString);
				System.out.println("Got roomNo from param = " + roomNo);
			} catch (NumberFormatException e) {
				// if get here, it's a bug: user can't directly enter room no.
				System.out.println("studentWelcome: bad number format in room");
				throw new ServletException(
						"Bad roomNo param in StudentWelcomeController");
			}
		}*/

		UserBean user = (UserBean) session.getAttribute("user");
		if (user == null)
			user = new UserBean();
		if (roomNo != null)
			student.setRoomNo(roomNo); // set newly obtained roomNo
		if (student.getRoomNo() > 0)
			roomNo = student.getRoomNo(); // just set or older setting
		session.setAttribute("user", user);

		List<MusicOrderData> report = null;
		Boolean hasBaked = false;
		try {
			System.out.println("in StudentWelcomeController pt B (isnull = "
					+ (studentService == null));
			if (roomNo != null && roomNo > 0) {
				report = studentService.getOrderStatus(roomNo);
				if (report != null)
					for (MusicOrderData order : report)
						if (order.getStatus() == MusicOrderData.BAKED)
							hasBaked = true;
			}
			Set<String> allSizes = studentService.getSizeNames();
			Set<String> allToppings = studentService.getToppingNames();
			System.out.println("#sizes = "+ allSizes.size());
			request.setAttribute("allSizes", allSizes);
			request.setAttribute("allToppings", allToppings);
		} catch (Exception e) {
			System.out.println("in StudentWelcomeController pt C");
			System.out.println(MusicSystemConfig.exceptionReport(e));
			throw new ServletException("OrderStatus Controller: ", e);
		}
		request.setAttribute("statusReport", report);
		request.setAttribute("hasBaked", hasBaked);
		request.setAttribute("numRooms", MusicSystemConfig.NUM_OF_ROOMS);
		return view;
	}
}
