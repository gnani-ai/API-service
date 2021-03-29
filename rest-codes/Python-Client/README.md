# Gnani RESTapi Client

- This is the python RESTapi Client package from Gnani.ai, which is used for speech recognition. 
- You can use this RESTapi for converting your audios to text form.

### Gnani speech API supports multiple languages:
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


## Installation

### Ubuntu
- `apt-get install python3.6`
- `apt-get install python3-pip`

### Mac OSX
- `brew install python3`
- `brew install python3-pip`
___
- update details in `user.config` and add certificate file in root directory
- `pip3 install requests`
- `python3 client.py &` 
- check the result in result.log file

### Python package link.
- For package link: [Package Link](https://pypi.org/project/gnani-asr-rest-api/0.0.1/)


### Installation of package. 
- `pip install gnani-asr-rest-api==0.0.1`

# Note:
- Please make sure you are running the python command from the directory which has certificate file and the audio file. 

### Import commands:
- `from gnani_rest_api import client`
- Enter the required inputs.

- To get the transcription run :
- `client.start(token, accesskey, encoding, lang_code, format)`
- Note: please enter the value of each input within single inverted comas ' ' example: client.start('dkflsdjlkfjs','dksfjcslkdfjcv','pcm17','hindi','wav')

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
<td markdown="span">Gujarathi</td>
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


### Support or Contact

#### Disclaimer
The Speech APIs are completely proprietary and are the sole property of Gnani.ai. We reserve the right to remove users access at any point of time. Note that the free access to the APIs are purely for testing or experimental purposes, and will be available to the users for a limited amount of time, after which they will have to purchase the commercial version. Gnani.ai will immediately remove access if the user is found to be using the APIs for commercial purposes. If you wish to obtain unlimited access, you can make an enquiry on the website or write to us at operations@gnani.ai. Also if you are having trouble please raise an issue or mail to us at operations@gnani.ai
