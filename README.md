# Proxy SMS
Proxy which transforms SMS messages to HTTP requests

## SMS Limit Workaround
Change limit to 30,000 SMS per minute

1. `adb shell`
2. `settings put global sms_outgoing_check_max_count 30000`
3. `settings put global sms_outgoing_check_interval_ms 6000`
4. Restart phone

## Endpoint

### Request
GET https://<endpoint>/api?body=`<incoming_sms>`?number=`<incoming_number>`

### Response
```
{
    "message": "<reply_message>"
}
```
