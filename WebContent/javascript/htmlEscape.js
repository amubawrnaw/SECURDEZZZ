function htmlEscape(unsafeString) {
    return unsafeString
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;");
 }

function checkIfValidRedirect(){
	if(window.location.href.includes(".jsp") || window.location.href.includes(".html"))
		window.location = "notAllowed.html";
}
var passwordRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!.,?@#\$%\^&\*])(?=.{8,})");
