package mypackage;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CarRentalNew extends HttpServlet {

    String jsonPath = "/home/alumne/apache-tomcat-9.0.37/webapps/my_webapp/vehicles.json";
    int cont = 0;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String nombre = req.getParameter("name");
        String model_vehicle = req.getParameter("model_vehicle");
        String sub_model_vehicle = req.getParameter("sub_model_vehicle");
        String dies_lloguer = req.getParameter("dies_lloguer");
        String num_vehicles = req.getParameter("num_vehicles");
        String descompte = req.getParameter("descompte");

        JSONObject obj = new JSONObject();
        obj.put("nombre", nombre);
        obj.put("model_vehicle", model_vehicle);
        obj.put("sub_model_vehicle", sub_model_vehicle);
        obj.put("dies_lloguer", dies_lloguer);
        obj.put("num_vehicles", num_vehicles);
        obj.put("descompte", descompte);

        File f = new File(jsonPath);
        if (f.exists()) {
            JSONParser parser = new JSONParser();

            try (Reader reader = new FileReader(jsonPath)) {
                JSONObject allVehicles = (JSONObject) parser.parse(reader);
                JSONArray allList = (JSONArray) allVehicles.get("Vehicles");
                if (allList == null) {
                    allList = new JSONArray();
                }
                allList.add(obj);

                JSONObject Vehicles = new JSONObject();
                Vehicles.put("Vehicles", allList);
                try (FileWriter file = new FileWriter(jsonPath)) {
                    file.write(Vehicles.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            JSONArray vehicles = new JSONArray();
            vehicles.add(obj);
            JSONObject allVehicles = new JSONObject();
            allVehicles.put("Vehicles", vehicles);

            try (FileWriter file = new FileWriter(jsonPath)) {
                file.write(allVehicles.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            
            out.println("<html><table> <tr> <td>Car Model: </td> <td>" + model_vehicle + "</td> </tr>"
                + "<tr> <td>Engine: </td> <td>" + sub_model_vehicle + "</td> </tr>"
                + "<tr> <td>Number of days: </td> <td>" + dies_lloguer + "</td> </tr>"
                + "<tr> <td>Number of units: </td> <td>" + num_vehicles + "</td> </tr>"
                + "<tr> <td>Discount: </td> <td>" + descompte + "</td> </tr> </table>"
                + "<a href=\"carrental_home.html\">Home</a></html>");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
