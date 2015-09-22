# MailSender
MailSender is simple RESTfull mail sending application. It combines the services of different email providers. 
Currently there are 3 providers implemented - [SendGrid](https://sendgrid.com/user/signup), [Mailgun](http://www.mailgun.com) and [Mandrill](https://mandrillapp.com) but more can be added easily. If one of the services goes down, MailSender failovers to another provider guaranteeing email delivery.

## Live Demo
You can see live demo here: [Click Me](http://martoup-mailsender.herokuapp.com/swagger-ui.html)
## How to use MailSender to send an email:
* You can use integrated Swagger UI([more info](http://swagger.io/))

  Open /swagger-ui.html:
  ![SwaggerUI](https://cloud.githubusercontent.com/assets/1436511/10029996/fa1843de-617d-11e5-9597-b67d5d02a283.PNG)
  You can click on [POST] /mail to expand the menu:
  ![SwaggerUI Menu](https://cloud.githubusercontent.com/assets/1436511/10031002/57e5db52-6183-11e5-8594-fb400af6b2c3.PNG)
  On top you can see example response.
  1. If you want to make a request - Press on the Model Schema in yellow to copy the schema.
  2. Edit the request values
  3. Press Try it out!
  
  If you click on Model

![SwaggerUI Model](https://cloud.githubusercontent.com/assets/1436511/10031001/56faaeac-6183-11e5-80dc-cc160a725efa.PNG)
  
You will see more info about the JSON request schema - which fields are optional and which are required.
  
* OR you can make simple HTTP Post request to /mail 

Here is an example Request JSON
```
{
  "to": [
    "test1@email.com",
    "test2@email.com"
  ],
  "subject": "Test Subject",
  "content": "Test Content",
  "from": "test3@email.com",
  "bcc": [
    "test4@email.com"
  ],
  "cc": [
    "test5@email.com"
  ]
}
```
##How to run it?
It very simple - clone the project and place your creditentials in authentication.properties.
```
mandrill.apiKey=
mandrill.url=https://mandrillapp.com/api/1.0/messages/send.json

sendgrid.username=
sendgrid.password=

mailgun.host=
mailgun.url=https://api.mailgun.net/v3/${mailgun.host}/messages
mailgun.apiKey=
```
