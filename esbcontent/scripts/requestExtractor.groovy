import org.jboss.soa.esb.message.*
import groovy.util.*

//Put the message in a local variable 
Message myMessage = message;

println "*********** Begin Service ***********"

def incomingXML = myMessage.getBody().get()

println "Extracting Variables"
def travelRequest = new XmlParser().parseText(incomingXML)
def bookAirTickets = travelRequest.bookAirTickets.text()
def reserveHotel = travelRequest.reserveHotel.text()
def airlines = travelRequest.airlines.text()

def customerName = travelRequest.customerName.text()
def cancelAirTickets = travelRequest.cancelAirTickets.text()
def cancelHotelReservation = travelRequest.cancelHotelReservation.text()

def airBookingSucceed = travelRequest.airBookingSucceed.text()
def hotelReservationSucceed = travelRequest.hotelReservationSucceed.text()
def makePayment = travelRequest.makePayment.text()
def paySucceed = travelRequest.paySucceed.text()

println "Returning Variables to Requester"
//myMessage.getBody().add("travelRequest", travelRequest)
myMessage.getBody().add("bookAirTickets", bookAirTickets)
myMessage.getBody().add("reserveHotel", reserveHotel)
myMessage.getBody().add("airlines", airlines)
myMessage.getBody().add("customerName", customerName)
myMessage.getBody().add("cancelAirTickets", cancelAirTickets)
myMessage.getBody().add("airBookingSucceed", airBookingSucceed)
myMessage.getBody().add("hotelReservationSucceed", hotelReservationSucceed)
myMessage.getBody().add("makePayment", makePayment)
myMessage.getBody().add("paySucceed", paySucceed)
myMessage.getBody().add("cancelHotelReservation", cancelHotelReservation)
//myMessage.getBody().add("hotelReservationRef", "")
//myMessage.getBody().add("airBookingRefNum", "")

println "************ End Service ************"