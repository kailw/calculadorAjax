/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.control;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kevin
 */
public class tablajax extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        DecimalFormat df = new DecimalFormat("#.##");        
        PrintWriter out = response.getWriter();
        Gson gSon = new Gson();
        String sancho = request.getParameter("ancho").trim();
        String slargo = request.getParameter("largo").trim();
        Double resultado = 0.0;
        String operador ="multiplicar";
        String exp ="^-?[0-9]+([.][0-9]+)?";
       
        int rand = (int)Math.round(Math.random() * 5 + 1);        
        try {
            /* TODO output your page here. You may use following sample code. */
            if (sancho.matches(exp) && slargo.matches(exp)) {
                Double operadorx = Double.parseDouble(sancho);
                Double operadory = Double.parseDouble(slargo);
                if (operador.equals("")) {
                    throw new IllegalArgumentException();
                } 
                    switch (operador) {
                        case "sumar":
                            resultado = operadorx + operadory;
                            break;
                        case "restar":
                            resultado = operadorx - operadory;
                            break;
                        case "multiplicar":
                            resultado = operadorx * operadory;
                            break;
                        case "dividir":
                            if (operadory == 0) {
                                throw new NumberFormatException();
                            } else {
                            resultado = operadorx / operadory;
                            break;
                            }
                        default:
                            throw new IllegalArgumentException();
                    }   
                    
                String strJson = gSon.toJson(df.format(resultado));
                TimeUnit.SECONDS.sleep(rand);
                out.print(strJson);

            } else {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException nfe) {            
            String error = "El divisor de una division no puedes ser 0.";
            String strJson = gSon.toJson(error);
            out.print(strJson);
        } catch (IllegalArgumentException iae) {           
            String error;
            if (sancho.equals("") || slargo.equals("")) {
                error = "No puede dejar campos vacios.";
            } else {
                if (!(operador.equals("sumar") || operador.equals("restar")) || operador.equals("multiplicar") || operador.equals("dividir")) {
                error = "Debes seleccionar un operador";
            } else {
                error = "Debes introducir solo numeros.";
                }
            }                
            String strJson = gSon.toJson(error);
            out.print(strJson);
        } catch (InterruptedException ex) {
            Logger.getLogger(tablajax.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
