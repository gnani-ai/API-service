## Welcome to GNANI API Service

Use our API to convert your Speech to Text

Here is a quick set-up guide to integrate Gnani speech API into your system.

Gnani speech API supports multiple languages:
- Kannada
- Hindi
- Indian English
- Singapore English
- United Kingdom English
- United States English
- Tamil
- Telugu
- Gujarati
- Marathi
- Bengali
- Malayalam
- Punjabi
- Urdu

## Authentication 
To get access to our API(s) visit [gnani.ai](https://www.gnani.ai/api/#ExploreAPI) to register yourself.

## Prequisites for setting up the API
- Token, accesskey and certificate received from gnani to your registered email id. This is mandatory to access the api.
- API URL : asr.gnani.ai
- PORT: 443
- Audio Format Supported : wav, amr-wb
- Audio Sampling Rate : 16kHz
- Number of Channels : 1
- Encoding Format : pcm16, amr-wb

### Note
Language Codes are as follows :
<table>
<colgroup>
<col width="30%" />
<col width="70%" />
</colgroup>
<thead>
<tr class="header">
<th>Language</th>
<th>Code</th>
</tr>
</thead>
<tbody>
<tr>
<td markdown="span">Kannada</td>
<td markdown="span">kn-IN</td>
</tr>
<tr>
<td markdown="span">Indian English</td>
<td markdown="span">en-IN</td>
</tr>
<tr>
<td markdown="span">Singapore English</td>
<td markdown="span">en-SG</td>
</tr>
<tr>
<td markdown="span">United Kingdom English</td>
<td markdown="span">en-GB</td>
</tr>
<tr>
<td markdown="span">United States English</td>
<td markdown="span">en-US</td>
</tr>
 <tr>
<td markdown="span">Tamil</td>
<td markdown="span">ta-IN</td>
</tr>
<tr>
<td markdown="span">Hindi</td>
<td markdown="span">hi-IN</td>
</tr>
<tr>
<td markdown="span">Telugu</td>
<td markdown="span">te-IN</td>
</tr>
  <tr>
<td markdown="span">Gujarati</td>
<td markdown="span">gu-IN</td>
</tr>
 <tr>
<td markdown="span">Marathi</td>
<td markdown="span">mr-IN</td>
</tr>
 <tr>
<td markdown="span">Malayalam</td>
<td markdown="span">ml-IN</td>
</tr>
 <tr>
<td markdown="span">Bengali</td>
<td markdown="span">bn-IN</td>
</tr>
 <tr>
<td markdown="span">Punjabi</td>
<td markdown="span">pa-guru-IN</td>
</tr>
 <tr>
<td markdown="span">Urdu</td>
<td markdown="span">ur-IN</td>
</tr>
</tbody>
</table>

## How to setup the API
- In your client grpc code, you are required to pass headers along with the audio chunks. The headers must contain token,accesskey sent to your email id, the language that you want to use, audioformat and encoding type as key value pairs. For example, the format to be followed is below : 
<table>
<colgroup>
<col width="30%" />
<col width="70%" />
</colgroup>
<thead>
<tr class="header">
<th>Key</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td markdown="span">token</td>
<td markdown="span">eyJhbGcIIUz.iOiJIUzI1NiIs-5cC.N2N2OWxBRUsxT</td>
</tr>
<tr>
<td markdown="span">lang</td>
<td markdown="span">en-IN</td>
</tr>
 <tr>
<td markdown="span">accesskey</td>
<td markdown="span">7f550a9f4c44173a37664d938f1</td>
 </tr>
 <tr>
<td markdown="span">audioformat</td>
<td markdown="span">wav (if amr-wb audioformat use amr-wb)</td>
</tr>
  <tr>
<td markdown="span">encoding</td>
<td markdown="span">pcm16 (if amr-wb audioformat use amr-wb as encoding style)</td>
 </tr>
</tbody>
</table>

- You are required to add certficate (cert.pem) that was sent to the registered email id as part of your code.

### Note 
- Your request will not be authenticated if you fail to add any of these headers to your request.
- Each registered user can only send 250 grpc requests of 15 seconds audio each, beyond which the user will be blocked. Also, user is allowed to send only 5 concurrent requests per second. If you wish to opt for extension , please send a mail to hello@gnani.ai
- We currently support streaming through GRPC and audio with the following properties:
<table>
<colgroup>
<col width="30%" />
<col width="70%" />
</colgroup>
 <tbody>
<tr>
<td markdown="span">Audio Format</td>
<td markdown="span">wav (if amr-wb audioformat use amr-wb)</td>
</tr>
<tr>
<td markdown="span">Encoding Format</td>
<td markdown="span">pcm16 (if amr-wb audioformat use amr-wb as encoding style)</td>
</tr>
</tbody>
</table>

## Grpc Error codes 
<table>
<tbody>
 </tbody>
</table>
<table>
<colgroup>
<col width="30%" />
<col width="20%" />
<col width="30%" />
</colgroup>
 <tbody>
 <thead>
<tr class="header">
<th>Code</th>
<th>Number</th>
<th>Description</th>
</tr>
</thead>
<tr>
<td markdown="span">OK</td>
<td markdown="span">0</td>
<td markdown="span">No error. Request is processed.</td>
</tr>
<tr>
<td markdown="span">CANCELLED</td>
<td markdown="span">1</td>
<td markdown="span">Operation was cancelled by the caller.</td>
</tr>
<tr>
<td markdown="span">PERMISSION_DENIED</td>
<td markdown="span">7</td>
<td markdown="span">The caller does not have permission to execute the specified operation. This occur if there is any issue in the header sent by the caller.
Example : grpc_message:"You are not authorised to use this language"</td>
</tr>
<tr>
<td markdown="span">INTERNAL</td>
<td markdown="span">13</td>
<td markdown="span">This means that some invariants expected by the underlying system have been broken. You can fnd the custom error details sent through grpc_message.
 Example : grpc_message:"Token is Invalid!" , "Free quota is over!"</td>
</tr>
  <tr>
<td markdown="span">UNAVAILABLE</td>
<td markdown="span">14</td>
<td markdown="span">The server is currently unavailable. Please wait for sometime and retry. If the problem persists contact hello@gnani.ai or raise an issue on github.</td>
</tr>
</tbody>
</table>

## Sample Code
Here are the list of sample codes.

GRPC Codes
- [Python](https://github.com/gnani-ai/API-service/tree/master/grpc-codes/Python3-Client)
- [Cpp](https://github.com/gnani-ai/API-service/tree/master/grpc-codes/Cpp-Client)
- [NodeJs](https://github.com/gnani-ai/API-service/tree/master/grpc-codes/Nodejs-Client) 
- [Java](https://github.com/gnani-ai/API-service/tree/master/grpc-codes/Java-Client)
- [C#](https://github.com/gnani-ai/API-service/tree/master/grpc-codes/C%23-Client)

REST Codes
- [NodeJs](https://github.com/gnani-ai/API-service/tree/master/rest-codes/Nodejs-Client)
- [Python](https://github.com/gnani-ai/API-service/tree/master/rest-codes/Python-Client)
- [Java](https://github.com/gnani-ai/API-service/tree/master/rest-codes/Java-Client)

### Support or Contact

#### Disclaimer
The Speech APIs are completely proprietary and are the sole property of Gnani.ai. We reserve the right to remove users access at any point of time. Note that the free access to the APIs are purely for testing or experimental purposes, and will be available to the users for a limited amount of time, after which they will have to purchase the commercial version. Gnani.ai will immediately remove access if the user is found to be using the APIs for commercial purposes. If you wish to obtain unlimited access, you can make an enquiry on the website or write to us at hello@gnani.ai. Also if you are having trouble please raise an issue or mail to us at hello@gnani.ai
