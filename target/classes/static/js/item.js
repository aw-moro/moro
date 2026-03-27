$(function() {
	$(".reset").click(function() {
		$("#item").val("");
        $("#itemCode").val("");
        $("#unitPrice").val("");
        $("#quantity").val("");
        $("#unit").val("");
        $("#itemDetail").val("");
		$("#salesTax").val("10");
		$("input[name='withholdingTax']").prop("checked", false);
	});

	$(".js_btn_regitser").click(function() {
		if (confirm("登録します。よろしいですか？")) {
			$("form").submit();
		}
	});

	$(".js_btn_update").click(function() {
		if (confirm("更新します。よろしいですか？")) {
			$("form").submit();
		}
	});

	$("#birthday").datepicker({
		dateFormat: 'yy/mm/dd',
	});
});
