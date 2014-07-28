package edu.ncsu.soc.esb;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import org.apache.log4j.Logger;

public class ResultPrinter extends AbstractActionLifecycle {
	
	protected ConfigTree config;
	private Logger logger = Logger.getLogger(ResultPrinter.class);
	
    public ResultPrinter(ConfigTree config) { this.config = config; } 
	
	public Message process(Message message) throws Exception {
		
		//boolean airTicketsBooked = Boolean.valueOf((String) message.getBody().get("airTicketsBooked"));
		//boolean hotelReserved = Boolean.valueOf((String) message.getBody().get("hotelReserved"));
		String airlines = (String) message.getBody().get("airlines");
		int bookAirTickets = Integer.valueOf((String) message.getBody().get("bookAirTickets"));
		boolean airBookingSucceed = Boolean.valueOf((String) message.getBody().get("airBookingSucceed"));
		boolean hotelReservationSucceed = Boolean.valueOf((String) message.getBody().get("hotelReservationSucceed"));
		String customerName = (String) message.getBody().get("customerName");
		String airBookingRefNum = (String) message.getBody().get("airBookingRefNum");
		int reserveHotel = Integer.valueOf((String) message.getBody().get("reserveHotel"));
		int hotelReservationRef = Integer.valueOf((String) message.getBody().get("hotelReservationRef"));
		boolean hotelReservationRefunded  = Boolean.valueOf((String) message.getBody().get("hotelReservationRefunded"));
		boolean airTicketsRefunded  = Boolean.valueOf((String) message.getBody().get("airTicketsRefunded"));
        
		if(airBookingSucceed){
			logger.info(airlines + " AIR BOOKING SUCCEEDED FOR " + bookAirTickets + ";CUSTOMER: " + customerName + "; BOOKING REF #" + airBookingRefNum);
		}else{
			logger.info(airlines + " AIR BOOKING FAILED FOR " + bookAirTickets + "; BOOKING REF #" + null);
		}
		
		
		if(hotelReservationSucceed){
			logger.info("HOTEL BOOKING SUCCEEDED FOR " + reserveHotel + "; CUSTOMER: " + customerName + "; RESERVATION REF # " + hotelReservationRef );
		}else{
			logger.info("HOTEL BOOKING FAILED FOR " + reserveHotel + "; RESERVATION REF # " + null );
		}
		
		if(airTicketsRefunded){
			logger.info(airlines + " AIR BOOKING CANCELLED FOR " + bookAirTickets + "; BOOKING REF #" + Integer.valueOf((String) message.getBody().get("airBookingRefNum")));
		}
		
		if(hotelReservationRefunded){
			logger.info("HOTEL RESERVATION CANCELLED FOR " + reserveHotel + "; RESERVATION REF # " + hotelReservationRef);
		}
        
		
				
		boolean airTicketsBooked = Boolean.valueOf((String) message.getBody().get("airTicketsBooked"));
		boolean hotelReserved = Boolean.valueOf((String) message.getBody().get("hotelReserved"));
		boolean paySucceed = Boolean.valueOf((String) message.getBody().get("paySucceed"));
		int makePayment = Integer.valueOf((String) message.getBody().get("makePayment"));
		
		if(!paySucceed){
			logger.info("PAYMENT FAILED FOR " + makePayment);
		}else{
			if(hotelReserved && airTicketsBooked){
				logger.info("PAYMENT SUCCEEDED FOR " + message.getBody().get("makePayment"));
			}else{
				logger.info("PAYMENT CANCELLED FOR " + message.getBody().get("makePayment"));
			}
			
		}
		
		return message;         	
	}
	
	private void printPayload(String displayName, Object payload) {
		
        if (payload instanceof String) {
			logger.info(displayName + ": " + payload);
		} else {
			logger.info(displayName + " is NULL");
		}		
	}
}	
