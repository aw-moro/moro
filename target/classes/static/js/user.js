$(function() {
	$(".reset").click(function() {
		$("#userName").val("");
		$("#userNameKana").val("");
		$("#mailaddress").val("");
		$("#prefCd").val("");
		$("#zipCd").val("");
		$("#address").val("");
		$("#tel").val("");
		$("#birthday").val("");
		$("input[name='gender']").prop("checked", false);
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
