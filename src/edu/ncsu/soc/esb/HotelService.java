package edu.ncsu.soc.esb;

import java.util.Random;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import org.apache.log4j.Logger;

public class HotelService extends AbstractActionLifecycle {

	protected ConfigTree config;
	private Logger logger = Logger.getLogger(HotelService.class);

	public HotelService(ConfigTree config) { this.config = config; } 

	public Message process(Message message) throws Exception {

		/* printPayload("buyTickets", message.getBody().get("buyTickets"));
        printPayload("returnTickets", message.getBody().get("returnTickets"));
        printPayload("movieName", message.getBody().get("movieName"));
        printPayload("reserveDinner", message.getBody().get("reserveDinner"));
        printPayload("cancelDinner", message.getBody().get("cancelDinner"));
        printPayload("customerName", message.getBody().get("customerName"));*/

		try{
			int reserveHotel = Integer.valueOf((String) message.getBody().get("reserveHotel"));
			boolean hotelReservationSucceed = Boolean.valueOf((String) message.getBody().get("hotelReservationSucceed"));			
			String customerName = (String) message.getBody().get("customerName");
			boolean cancelHotelReservation  = Boolean.valueOf((String) message.getBody().get("cancelHotelReservation"));

			if(cancelHotelReservation){
				int hotelReservationRef = Integer.valueOf((String) message.getBody().get("hotelReservationRef"));
				message.getBody().add("hotelReservationRefunded", "true");
				message.getBody().add("hotelReserved", "false");				
			}else{
				if (reserveHotel > 0){

					if(hotelReservationSucceed){
						Random rand = new Random();
						int hotelReservationRef = rand.nextInt(Integer.MAX_VALUE);
						
						message.getBody().add("hotelReserved", "true");
						message.getBody().add("hotelReservationRef", String.valueOf(hotelReservationRef));
						
						message.getBody().add("cancelHotelReservation", "true");

					}else{
						
						message.getBody().add("hotelReserved", "false");
						
					}
				}

				else{
					logger.info("hotel reservations not available for " + reserveHotel);
				}
			}
		}
		catch (Exception e){
			logger.info("hotel service error: "+e.getMessage());
		}

		return message;         	
	}

	/*private void printPayload(String displayName, Object payload) {

        if (payload instanceof String) {
			logger.info(displayName + ": " + payload);
		} else {
			logger.info(displayName + " is NULL");
		}		
	}*/

}