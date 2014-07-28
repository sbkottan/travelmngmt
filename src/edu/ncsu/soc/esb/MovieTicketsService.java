package edu.ncsu.soc.esb;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import org.apache.log4j.Logger;

public class MovieTicketsService extends AbstractActionLifecycle {
	
	protected ConfigTree config;
	private Logger logger = Logger.getLogger(MovieTicketsService.class);
	
    public MovieTicketsService(ConfigTree config) { this.config = config; } 
	
	public Message process(Message message) throws Exception {
		
       /* printPayload("buyTickets", message.getBody().get("buyTickets"));
        printPayload("returnTickets", message.getBody().get("returnTickets"));
        printPayload("movieName", message.getBody().get("movieName"));
        printPayload("reserveDinner", message.getBody().get("reserveDinner"));
        printPayload("cancelDinner", message.getBody().get("cancelDinner"));
        printPayload("customerName", message.getBody().get("customerName"));*/
		Boolean ticketsBooked, ticketsRefunded ;
		ticketsBooked = Boolean.FALSE;
		ticketsRefunded = Boolean.FALSE;
		
		try{
			Integer returnTickets = Integer.valueOf((String) message.getBody().get("returnTickets"));
			if (returnTickets==0)
			{
				if (Integer.valueOf((String) message.getBody().get("buyTickets"))<16){
					ticketsBooked = Boolean.TRUE;	 
					logger.info("movie service procured "   + message.getBody().get("buyTickets") + " tickets for " + message.getBody().get("movieName"));
				}
				else{
					logger.info("movie service unable to procure " +message.getBody().get("buyTickets") + " tickets");
				}
			}
			else
			{
				ticketsRefunded = Boolean.TRUE;
				logger.info("movie service refunds "   + message.getBody().get("returnTickets") + " tickets for " +message.getBody().get("movieName"));
			}
			
			}
		catch (Exception e){
			logger.info("movie service error: "+e.getMessage());
		}
        message.getBody().add("ticketsBooked",ticketsBooked.toString());
        message.getBody().add("ticketsRefunded",ticketsRefunded.toString());
        logger.info(ticketsBooked.toString() + " movie service returns");
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