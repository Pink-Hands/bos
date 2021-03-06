package pers.test.crm;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ICustomerService", targetNamespace = "http://service.crm.test.pers/")
@XmlSeeAlso({

})
public interface ICustomerService {


    /**
     * 
     * @param arg0
     * @return
     *     returns pers.test.crm.service.Customer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findCustomerByTelephone", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindCustomerByTelephone")
    @ResponseWrapper(localName = "findCustomerByTelephoneResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindCustomerByTelephoneResponse")
    public Customer findCustomerByTelephone(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "updatedecidedzone", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.Updatedecidedzone")
    @ResponseWrapper(localName = "updatedecidedzoneResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.UpdatedecidedzoneResponse")
    public void updatedecidedzone(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "assigncustomersuntodecidedzone", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.Assigncustomersuntodecidedzone")
    @ResponseWrapper(localName = "assigncustomersuntodecidedzoneResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.AssigncustomersuntodecidedzoneResponse")
    public void assigncustomersuntodecidedzone(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "assigncustomerstodecidedzone", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.Assigncustomerstodecidedzone")
    @ResponseWrapper(localName = "assigncustomerstodecidedzoneResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.AssigncustomerstodecidedzoneResponse")
    public void assigncustomerstodecidedzone(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        List<Integer> arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findDecidedzoneIdByAddress", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindDecidedzoneIdByAddress")
    @ResponseWrapper(localName = "findDecidedzoneIdByAddressResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindDecidedzoneIdByAddressResponse")
    public String findDecidedzoneIdByAddress(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<pers.test.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findListHasAssociation", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindListHasAssociation")
    @ResponseWrapper(localName = "findListHasAssociationResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindListHasAssociationResponse")
    public List<Customer> findListHasAssociation(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<pers.test.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindAllResponse")
    public List<Customer> findAll();

    /**
     * 
     * @return
     *     returns java.util.List<pers.test.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findListNotAssociaton", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindListNotAssociaton")
    @ResponseWrapper(localName = "findListNotAssociatonResponse", targetNamespace = "http://service.crm.test.pers/", className = "pers.test.crm.service.FindListNotAssociatonResponse")
    public List<Customer> findListNotAssociaton();

}
