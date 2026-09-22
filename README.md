#Problem Description

This Identification Service is a REST service that returns make, 
model and type information from our database. The code has been
provided and as you can see all requests directly cause a database 
query to be executed. 

In the past this has served us well: Typical usage was that an 
end-user would stick with a certain selection for some time. And the 
well behaving J2EE applications that we build for these end-users
made proper use of sessions and/or caching. So they never flooded
this identification server with a lot of requests.

Nowadays this is different, first we started with number-plate 
lookup services that also use this Identification Service. These
services became very popular and cause seemingly random requests
to our identification data. And these are many. 

And besides that we've opened the Identification Service to third
party clients as well, and that caused unexpected usage of the
service. Although we charge per request (bundle) these JS/Python/PHP
clients appear not to do much caching on their side. We sometimes 
see a client doing exactly these same request about 20/30 times in
a couple of minutes. And strangely enough they never complain 
about the high costs directly, but they comment on the 
services being slow at times, which they don't expect this at this
price-point.

In short: the database cannot keep up with all these requests and
something needs to happen. 

#Solutions needed

We need to review the operational aspect of this application and
are interested too see if you have some ideas on this matter.
We'd appreciate it is if you can put (at leasts one) of these ideas
in practice and show us in code how this actually could work.

You are allowed to use whatever you see fit. If you need additional
tools and services you can run them locally our you can use 
AWS Free Tier that is all up to you.
