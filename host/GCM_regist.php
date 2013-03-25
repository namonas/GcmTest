<?php

$url = 'https://android.googleapis.com/gcm/send';

$registration_id = 'APA91bFLoeqkQLUwQDgzldRSWMFRekj9WbHZjU2ahBRdHJX6b928isGbgaq5WxOMP5tyitzC9Vw01ru1NIgZFNeK9mxAonk7hK_B17BShSRGp8sbOo1Atwnm78H2C1NwdC7fWvJCeXQIaaXnNoizX4VEqz7D5S9SAw';

$now = date("Y/m/d H:i:s");
$name = gethostname();
$message = $now.': GCM PUSH Notifiy from '.$name;

$header = array(
  'Content-Type: application/x-www-form-urlencoded;charset=UTF-8',
  'Authorization: key=AIzaSyDwtcp-wwdU46ENVjXDez5il8MUSBZOCs4',
);
$post_list = array(
 'registration_id' => $registration_id,
 'collapse_key' => 'update',
 'data.message' => $message,
);
$post = http_build_query($post_list, '&');

$ch = curl_init($url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_FAILONERROR, 1);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
curl_setopt($ch, CURLOPT_POST, TRUE);
curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
curl_setopt($ch, CURLOPT_TIMEOUT, 5);
$ret = curl_exec($ch);

var_dump($ret);

?>

