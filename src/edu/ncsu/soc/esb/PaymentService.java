package edu.ncsu.soc.esb;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import org.apache.log4j.Logger;

public class PaymentService extends AbstractActionLifecycle {

	protected ConfigTree config;
	private Logger logger = Logger.getLogger(PaymentService.class);

	public PaymentService(ConfigTree config) { this.config = config; } 

	public Message process(Message message) throws Exception {

		/* printPayload("buyTickets", message.getBody().get("buyTickets"));
        printPayload("returnTickets", message.getBody().get("returnTickets"));
        printPayload("movieName", message.getBody().get("movieName"));
        printPayload("reserveDinner", message.getBody().get("reserveDinner"));
        printPayload("cancelDinner", message.getBody().get("cancelDinner"));
        printPayload("customerName", message.getBody().get("customerName"));*/

		try{
			int makePayment = Integer.valueOf((String) message.getBody().get("makePayment"));
			boolean paySucceed = Boolean.valueOf((String) message.getBody().get("paySucceed"));		

			if (makePayment > 0){
				if(paySucceed){
					message.getBody().add("paySucceed", "true");
				}else{
					
					message.getBody().add("paySucceed", "false");
					message.getBody().add("airTicketsBooked", "false");
					message.getBody().add("hotelReserved", "false");
					message.getBody().add("cancelAirTickets", "true");
					message.getBody().add("cancelHotelReservation", "true");
				}

			}
			else{
				logger.info("makePayment cannot be less than zero");
			}
		}
		catch (Exception e){
			logger.info("payment service error: "+e.getMessage());
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