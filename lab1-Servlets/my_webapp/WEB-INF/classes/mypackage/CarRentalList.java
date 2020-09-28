package mypackage;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Iterator;

public class CarRentalList extends HttpServlet {

	String jsonPath = "/home/alumne/apache-tomcat-9.0.37/webapps/my_webapp/vehicles.json";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String nombre = req.getParameter("userid");
		String password = req.getParameter("password");

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");

		if (nombre == null || password == null || (!nombre.equals("admin") && !password.equals("admin"))) {
			sb.append("<big>You are not admin :) </big>");
		} else {

			File f = new File(jsonPath);
			if (f.exists()) {
				JSONParser parser = new JSONParser();
				try (Reader reader = new FileReader(jsonPath)) {
					JSONObject allVehicles = (JSONObject) parser.parse(reader);
					JSONArray allList = (JSONArray) allVehicles.get("Vehicles");
					if (allList == null) {
						sb.append("<big> The list is empty!! </big>");

					} else {
						for (int i = 0; i < allList.size(); i++) {
							JSONObject vehicle = (JSONObject) allList.get(i);
							sb.append("<table><tr><td>Car Model: </td><td>" + vehicle.get("model_vehicle")
									+ "</td></tr>" + "<tr><td>Engine:  </td><td>" + vehicle.get("sub_model_vehicle")
									+ "</td></tr>" + "<tr><td>Number of days: </td><td>" + vehicle.get("dies_lloguer")
									+ "</td></tr>" + "<tr><td>Number of units: </td><td>" + vehicle.get("num_vehicles")
									+ "</td></tr>" + "<tr><td>Discount: </td><td>" + vehicle.get("descompte")
									+ "</td></tr></table><br>");
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		}
		sb.append("<a href=\"carrental_home.html\">Home</a></html>");
		out.println(sb.toString());
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
