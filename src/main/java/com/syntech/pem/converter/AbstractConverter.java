//package com.syntech.pem.converter;
//
//import com.syntech.pem.model.BaseIdEntity;
//import com.syntech.pem.repository.GenericRepository;
//import java.util.logging.Logger;
//import javax.enterprise.context.RequestScoped;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.convert.Converter;
//import javax.inject.Inject;
//
//@RequestScoped
//public abstract class AbstractConverter implements Converter {
//
//     
//
//    /**
//     *
//     * @return
//     */
//    protected abstract GenericRepository getFacade();
//
//    @Override
//    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//        if (value == null || value.length() == 0 || value.equals("") || value.equalsIgnoreCase("null") || value.toLowerCase().startsWith("sel")) {
//            return null;
//        }
//        try {
//            return getFacade().findById(getKey(value));
//        } catch (NumberFormatException nfe) {
//            System.out.println("Severe: Number format Exception, Can't convert value " + value);
//            return null;
//        }
//    }
//
//    java.lang.Long getKey(String value) {
//        java.lang.Long key;
//        key = Long.valueOf(value);
//        return key;
//    }
//
//    String getStringKey(Object value) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(value);
//        return sb.toString();
//    }
//
//    @Override
//    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//        if ("".equals(object)) {
//            return "";
//        }
//        if (object == null) {
//            return null;
//        }
//        if (object instanceof BaseIdEntity) {
//            BaseIdEntity o = (BaseIdEntity) object;
//            return getStringKey(o.getId());
//        } else {
//            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: converter for " + this.getClass());
//        }
//    }
//}
