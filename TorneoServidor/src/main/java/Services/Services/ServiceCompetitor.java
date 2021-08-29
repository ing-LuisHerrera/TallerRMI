/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.Services;


import Models.Competitor;
import Repository.ConnectionDB;
import Services.Interfaces.IServiceCompetitor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author Luis
 */
public class ServiceCompetitor  extends UnicastRemoteObject implements IServiceCompetitor{

    private ConnectionDB connection;
    
    public ServiceCompetitor(ConnectionDB connection)throws RemoteException{
       this.connection = connection;
    }
    
    
    @Override
    public Competitor searchCompetitorById(long id) throws RemoteException{
        
        String cad = "SELECT * FROM participante WHERE id ="+id;
        ResultSet res = null;
        Competitor searched = null;
        
        try {
            res = connection.executeQueryStatement(cad);
            while(res.next()){
                
                searched.setId(Integer.parseInt(res.getString(1)));
                searched.setApodo(res.getString(2));
                searched.setFechaInscripcion(res.getObject(3, Date.class));
                searched.setFechaCaducidad(res.getObject(4, Date.class));
                        
            }
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        return searched;
        
    }

    @Override
    public void updateCompetitor(Competitor competitor)throws RemoteException {
        
        String cad = "UPDATE participante SET apodo ="+competitor.getApodo()+
                "fecha_inscripcion =" + competitor.getFechaInscripcion()+
                "fecha_caducidad ="+competitor.getFechaCaducidad()+
                "WHERE id = "+competitor.getId();
        try {
            if (!connection.executeUpdateStatement(cad)) {
                throw new Exception("Operacion no ejecutada");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

    @Override
    public void deleteCompetitor(long id)throws RemoteException {
        
        
        String cad = "DELETE FROM participante WHERE id="+id;
        try {
            if (!connection.executeUpdateStatement(cad)) {
                throw new Exception("Operacion no ejecutada");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void createCompetitor(Competitor competitor)throws RemoteException {
        
        
        String cad = "INSERT INTO participante VALUES("+  competitor.getId()   +",'"
                                                + competitor.getApodo()+"','"
                                                + competitor.getFechaInscripcion()+"','"
                                                + competitor.getFechaCaducidad()+ "')";

        

        try {
            if (!connection.executeUpdateStatement(cad)) {
                throw new Exception("Operacion no ejecutada");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public List<Competitor> searchAll() throws RemoteException {
        
        try {
            String cad = "SELECT * FROM participante";
            ResultSet res;
            ArrayList<Competitor> competitors = new ArrayList<>();

            int id;
            String apodo;
            Date fechaInscripcion;
            Date fechaCaducidad;

            res = connection.executeQueryStatement(cad);
            while(res.next()){

                id = Integer.parseInt(res.getString(1));
                apodo = res.getString(2);
                fechaInscripcion = res.getObject(3, Date.class);
                fechaCaducidad = res.getObject(4, Date.class);

                competitors.add(new Competitor(id, apodo, fechaInscripcion, fechaCaducidad));
                              
            }
            return competitors;
        } catch (SQLException ex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    
    
    
    
    
    
}
