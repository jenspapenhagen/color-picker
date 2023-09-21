# color-picker

This project uses Quarkus, the Supersonic Subatomic Java Framework.
Playing around with JAVA 21 and data driven way for microservice ideas.

This is a simple backend with a single HTTP JSON Endpoint. 
Calling with an RGB color and getting the color values for RGB CMYK, HSL and YUV back

Call:
> http://localhost:8080/color?red=20&green=254&blue=53 
 
Response:
```JSON {
"colors": [
{
"blue":53,
"green":254,
"red":20
} , {
"black":0.39215686274509665,
"cyan":92.0,
"magenta":0.0,
"yellow":79.0
} , {
"hue":128.46153259277344,
"lightness":13700.0,
"saturation":-86.02941131591797
} , {
"u":17152,
"v":27,
"y":10551296
}]
}
```