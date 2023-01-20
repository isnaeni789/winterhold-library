var moneyConversions = document.querySelectorAll(".moneyConversion");
for (let element of moneyConversions) {
  let convertedValue = Number(element.value).toFixed(2);
  element.value = convertedValue;
}
var numberInputs = document.querySelectorAll("[type=number]");
for (let element of numberInputs) {
  if (element.value == "") {
    element.value = 0;
  }
}
var dateInputs = document.querySelectorAll("[type=date]");
for (let element of dateInputs) {
  if (element.getAttribute("value") != "") {
    let dateValue = new Date(element.getAttribute("value"));
    let formatted = dateValue.toISOString().split("T")[0];
    element.value = formatted;
  }
}
let alternateAction = document.querySelector(".alternate-action");
if (alternateAction != null) {
  let actionType = alternateAction.getAttribute("data-action");
  document
    .querySelector(".alternate-action")
    .setAttribute("action", actionType.toLowerCase());

  if (actionType === "Update") {
    document.querySelector(".readonly-id").setAttribute("readonly", "");
    $(".readonly-select").prop("disabled", true);
  } else {
    $(".loanDate").val(new Date().toISOString().slice(0, 10));
  }
}

$(".loanDate").attr("readonly", "");
$(".loan-form").on("submit", function () {
  $(".readonly-select").prop("disabled", false);
});

let breadCrumb = document.querySelector(".breadcrumb-link");
let link = breadCrumb.getAttribute("data-href");
breadCrumb.setAttribute("href", link.toLowerCase());
