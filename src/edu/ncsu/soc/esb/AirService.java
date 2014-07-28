package edu.ncsu.soc.esb;

import java.util.Random;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import org.apache.log4j.Logger;

public class AirService extends AbstractActionLifecycle {

	protected ConfigTree config;
	private Logger logger = Logger.getLogger(AirService.class);

	public AirService(ConfigTree config) { this.config = config; } 

	public Message process(Message message) throws Exception {

		/* printPayload("buyTickets", message.getBody().get("buyTickets"));
        printPayload("returnTickets", message.getBody().get("returnTickets"));
        printPayload("movieName", message.getBody().get("movieName"));
        printPayload("reserveDinner", message.getBody().get("reserveDinner"));
        printPayload("cancelDinner", message.getBody().get("cancelDinner"));
        printPayload("customerName", message.getBody().get("customerName"));*/

		try{
			int bookAirTickets = Integer.valueOf((String) message.getBody().get("bookAirTickets"));
			boolean airBookingSucceed = Boolean.valueOf((String) message.getBody().get("airBookingSucceed"));
			String airlines = (String) message.getBody().get("airlines");
			String customerName = (String) message.getBody().get("customerName");
			boolean cancelAirTickets  = Boolean.valueOf((String) message.getBody().get("cancelAirTickets"));

			if(cancelAirTickets){
				message.getBody().add("airTicketsRefunded", "true");
				message.getBody().add("airTicketsBooked", "false");
			}else{
				if (bookAirTickets > 0){					
					if(airBookingSucceed){
						Random rand = new Random();
						int airBookingRefNum = rand.nextInt(Integer.MAX_VALUE);
						message.getBody().add("airTicketsBooked", "true");
						message.getBody().add("airBookingRefNum", String.valueOf(airBookingRefNum));
						message.getBody().add("cancelAirTickets", "true");
					}else{
						
						message.getBody().add("airTicketsBooked", "false");
						
					}
				}
				else{
					logger.info("air booking not available for " + bookAirTickets);
				}
			}
		}
		catch (Exception e){
			logger.info("air service error: " + e.getMessage());
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