# simple-webview
![alt text](https://github.com/iamRanjitgoud/simple-webview/blob/main/screen.gif)
# How does WebView work in Android? <br />
WebView is a view that display web pages inside your application. You can also specify HTML string and can show it inside your application using WebView. WebView makes turns your application to a web application. <br />
That's all you need for a basic WebView that displays a web page. Additionally, you can customize your WebViewby modifying the following:<br />
•	Enabling fullscreen support with WebChromeClient. This class is also called when a WebView needs permission to alter the host app's UI, such as creating or closing windows and sending JavaScript dialogs to the user. <br />
•	Handling events that impact content rendering, such as errors on form submissions or navigation with WebViewClient. You can also use this subclass to intercept URL loading.<br />
•	Enabling JavaScript by modifying WebSettings.<br />
•	Using JavaScript to access Android framework objects that you have injected into a WebView.<br />
# Using JavaScript in WebView <br />
•	If the web page you plan to load in your WebView uses JavaScript, you must enable JavaScript for your WebView. Once JavaScript is enabled, you can also create interfaces between your app code and your JavaScript code.


