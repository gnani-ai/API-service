## Welcome to GNANI API Service

Use our API to convert your Speech to Text

Here is a quick set-up guide to integrate Gnani speech API into your system.

Gnani speech API supports multiple languages:
- Kannada
- Hindi
- Indian English
- Tamil
- Telugu
- Gujarathi

## Authentication 
To get access to our API's visit [Link](https://gnani.ai) to register yourself.

## Prequisites for setting up the API
1. At the time of registration the token , username and password would have been mailed to your registered email id without which you will not be authenticated to access our API's
2. API URL : [Link](https://gnani.ai)
3. Audio Format Supported - Wav
4. Sampling Rate - 16000 or 8000
5. Channels - Mono
6. Encoding - PCM 16 bit

### Note
Language Codes are as follows :
<table>
<colgroup>
<col width="30%" />
<col width="70%" />
</colgroup>
<thead>
<tr class="header">
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td markdown="span">Kannada</td>
<td markdown="span">ka</td>
</tr>
<tr>
<td markdown="span">English</td>
<td markdown="span">en</td>
</tr>
 <tr>
<td markdown="span">Tamil</td>
<td markdown="span">ta</td>
</tr>
<tr>
<td markdown="span">Hindi</td>
<td markdown="span">hin</td>
</tr>
<tr>
<td markdown="span">Telugu</td>
<td markdown="span">te</td>
</tr>
  <tr>
<td markdown="span">Gujarathi</td>
<td markdown="span">gu</td>
</tr>
</tbody>
</table>

## How to setup the API
- In your client grpc code , you are required to pass headers along with the audio chunks. The headers must contain token , username , password sent to your email id and the language that you want to use. The format to be followed is below : 
<table>
<colgroup>
<col width="30%" />
<col width="70%" />
</colgroup>
<thead>
<tr class="header">
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td markdown="span">token</td>
<td markdown="span">your_token</td>
</tr>
<tr>
<td markdown="span">lang</td>
<td markdown="span">language_code</td>
</tr>
 <tr>
<td markdown="span">username</td>
<td markdown="span">your_username</td>
</tr>
  <tr>
<td markdown="span">password</td>
<td markdown="span">your_password</td>
</tr>
</tbody>
</table>

### Note 
Your request will not be authneticated if you fail to add any of these headers to your request.

## Sample Code
Here are the list of sample codes for Python and Nodejs.
- Python :[Link]
- Nodejs :[Link]
We will be adding other languages soon.

### Support or Contact

If you are having trouble please raise an issue or mail to us at email_id
