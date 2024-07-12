
// 크기&확장자 제한
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

var maxSize = 1048576; // 5MB

function checkExtentsion(fileName, fileSize) {
	if (fileSize >= maxSize) {
		alert("파일 사이즈 초과");
		return false;
	}

	if (regex.test(fileName)) {
		alert("해당 종류의 파일은 업로드할 수 없습니다.")
		return false;
	}
	return true;
}


// 현재 년월일 표시
function currentDay() {
	date = new Date();
	year = date.getFullYear();
	month = date.getMonth() + 1;
	day = date.getDate();

	$('.cur_year').html(year + '-');
	$('.cur_month').html(month + '-');
	$('.cur_day').html(day);
}

// 모달 초기화
$('#reportModal').on('show.bs.modal', function() {
	$('.smoothscroll.scroll-top').attr('style', 'display:none');
	$(this).find('form')[0].reset();
	$('#reportModal select option').remove();
	$('#reportModal select').append(`<option selected>유형 선택</option>`);
	$('#reportModal input[name="uploadFile"]').val('');
	$('#reportModal .uploadResult .row div').remove();
})
$('#reportModal').on('hide.bs.modal', function() {
	$('.smoothscroll.scroll-top').removeAttr('style', 'display:none');
	
	$('#reportModal .uploadResult .row div').remove();
	
})
$('#reportInfoModal').on('show.bs.modal', function() {
	$('.smoothscroll.scroll-top').attr('style', 'display:none');
	$('#reportInfoModal .uploadResult .row div').remove();
})
$('#reportInfoModal').on('hide.bs.modal', function() {
	$('.smoothscroll.scroll-top').removeAttr('style', 'display:none');
	$(this).find('form')[0].reset();
	$('#reportInfoModal .uploadResult .row div').remove();
})

function reportCloseAction(){
	console.log('close');
if(uploadFiles != ''){
	fileDelete(uploadFiles);
	uploadFiles = '';
	}
}

// 등록된 공사 보고 정보
var modalData = '';
// 첨부 이미지 정보
var uploadFiles = '';

// 파일 첨부 초기화
function fileUploadClick() {

	$('input[name="uploadFile"]').val('');
	$('.uploadResult .row div').remove();
};



// 파일 삭제
function fileDelete(uploadFiles) {
	$.ajax({
		url: '/rptFileDelete',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(uploadFiles),
		async: false
	})
}

function fileList() {

	var x = document.getElementById("multiFile");

	if ('files' in x) {
		if (x.files.length > 5) {
			alert("파일 개수가 초과되었습니다.");
			document.getElementById("multiFile").value = "";
			return;
		}
	}

	var formData = new FormData();

	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;

	if (uploadFiles != '') {
		console.log('del');
		fileDelete(uploadFiles);
		uploadFiles = '';
	}
	
	// formData에 데이터 넣기
	for (var i = 0; i < files.length; i++) {

		if (!checkExtentsion(files[i].name, files[i].size)) {
			return false;
		}
		if (files[i].type.indexOf('image') == -1) {
			alert("이미지 파일을 등록해 주세요.")
			return false;
		}

		formData.append("uploadFile", files[i]);
	}

	// Ajax
	$.ajax({
		url: '/rptFileInsert',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'
		, success: function(result) {
			//console.log(result);
			showUpFile(result);
			if (result != null) {
				uploadFiles = result;
			}
		}
	})
};
// 이미지 표시
function showUpFile(uploadResultArr) {

	if (!uploadResultArr || uploadResultArr.length == 0)
		return;

	var uploadUL = $('#reportModal .uploadResult .row');

	var str = "";

	$(uploadResultArr)
		.each(
			function(i, obj) {
				str = imageshow(obj);
			});

	uploadUL.append(str);
}


// 등록 모달 열기
function reportInsertForm(no) {
	
	uploadFiles = '';

	$('#reportModal').modal('show');

	currentDay(); // 현재 날짜 표시

	$('#reportModal .modal-footer button:eq(0)').html('등록');
	$('#reportModal .modal-footer button:eq(0)').removeAttr('onClick');
	$('#reportModal .modal-footer button:eq(0)').attr('onClick', 'reportInsertAction()');
	$('#reportModal h1 b').html('공사 보고 등록');

	$.ajax({
		url: '/rptDivi?contractNo=' + no,
		type: 'Get',

		success: function(result) {
			$('input[name="contractNo"]').val(result.contractNo);
			selectOption(result); // 공사 유형 표시
		}

	})
}

// 등록
function reportInsertAction() {

	selectData(); // 공사 유형 선택 값 넣기

	// 폼 데이터 전송
	var formData = new FormData(document.reportInsertForm);

	for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}

	$.ajax({
		url: '/rptInsertInfo',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'

		, success: function(result) {
			rptInfo(result); // 상세 정보 가져오기
			fileDelete(uploadFiles);
		}
	})
}
// 상세 정보 가져오기
function rptInfo(no) {

	$.ajax({
		url: '/rptInfo?CttReportNo=' + no,
		type: 'Get'

		, success: function(result) {
			modalInfo(result); // 상세 내역 표시
		}
	})
}
// 상세 모달 열기
function reportInfoForm(no) {

	$('#reportInfoModal').modal('show');

	$.ajax({
		url: '/rptInfo?CttReportNo=' + no,
		type: 'Get'

		, success: function(result) {
			console.log(result);
			modalData = result;

			$('#reportModal').modal('hide');

			modalInfo(result);
		}
	})
}

// 섬네일 이미지 명 가져오기
$('.uploadResult').on("click", "img", function(e) {
	var obj = e.target;

	var newobj = '';
	newobj = obj.src.replace("s_", '');

	showImage(newobj); // 원본 이미지 표시

})
// 삭제
$('#rpt_deleteBtn').on("click", function() {

	$.ajax({
		url: '/rptDelete',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(modalData.fileList),
		async: false

	})
	location.href = '/test';
})
// 수정 모달 열기
$('#rpt_updateBtn').on('click', function() {
	
	uploadFiles = '';

	$.ajax({
		url: '/rptDivi?contractNo=' + $('input[name="contractNo"]').val(),
		type: 'Get',

		success: function(result) {

			selectOption(result);
			$('input[name="contractNo"]').val(result.contractNo);

			$.ajax({
				url: '/rptInfo?CttReportNo=' + $('input[name="cttReportNo"]').val(),
				type: 'Get'

				, success: function(result) {

					modalData = result;
					currentDay();
					modalInsertInfo(result);
					$('#reportModal').modal('show');

				}

			})
		}
	})

})

// 원본 이미지 확인
function showImage(img) {

	$('.bigPictureWrapper').css("display", "flex").show();
	$('.bigPicture').html(`<img src=${img}>`);
}

$('.bigPictureWrapper').on("click", function() {
	$('.bigPictureWrapper').hide();
})

// 수정
function reportUpdateAction() {

	selectData();

	// 폼 데이터 전송
	var formData = new FormData(document.reportInsertForm);

	for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}

	$.ajax({
		url: '/rptUpdate',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'

		, success: function(result) {
			fileDelete(uploadFiles);
			modalInfo(result);
			$('#reportInfoModal').modal('show');
		}
	})

}

// 셀렉트 데이터 선택
function selectData() {
	if ($('select').val() !== '유형 선택') {
		$('input[name=cttDivision]').val($('select').val());
	} else {
		$('input[name=cttDivision]').val(null);
	}
}


// 셀렉트 선택
function selectOption(result) {
	for (i = 0; i < result.divi.length; i++) {
		$('select').append(`<option>${result.divi[i]}</option>`);
	}
}


// 모달 정보 표기
function modalInfo(result) {
	$('#reportModal').modal('hide');

	$('input[name="cttReportNo"]').val(result.cttReportNo);
	$('input[name="contractNo"]').val(result.contractNo);
	$('input[name="fileId"]').val(result.fileId);

	$('#title').html(result.title);
	if (result.accessState == 'N') {
		$('#accessState').html('미승인');
	} else {
		$('#accessState').html('승인');
	}
	$('#cttDivision').html(result.cttDivision);
	$('textarea[name="detailContent"]').val(result.detailContent);
	$('#cttReportDt').html(result.cttReportDt.substring(0, 10));

	var uploadUL = $('#reportInfoModal .uploadResult .row');

	var str = "";

	result.fileList.forEach(e => {

		str = imageshow(e);

	})
	uploadUL.append(str);
}

// 모달 수정 정보 표기
function modalInsertInfo(result) {
	$('#reportInfoModal').modal('hide');
	$('input[name="cttReportNo"]').val(result.cttReportNo);
	$('input[name="fileId"]').val(result.fileId);
	$('input[name="title"]').val(result.title);
	$('textarea[name="detailContent"]').val(result.detailContent);
	$('input[name="cttReportDt"]').html(result.cttReportDt.substring(0, 10));

	$('select option:eq(0)').removeAttr('selected');

	for (i = 0; i < $('option').length; i++) {
		if ($('option:eq(' + i + ')').html() === result.cttDivision) {
			$('option:eq(' + i + ')').attr('selected', 'selected');
		}
	}
	var uploadUL = $('#reportModal .uploadResult .row');

	var str = "";

	result.fileList.forEach(e => {

		str = imageshow(e);

	})
	uploadUL.append(str);

	$('#reportModal .modal-footer button:eq(0)').html('수정');
	$('#reportModal .modal-footer button:eq(0)').removeAttr('onClick');
	$('#reportModal .modal-footer button:eq(0)').attr('onClick', 'reportUpdateAction()');
	$('#reportModal h1 b').html('공사 보고 수정');
}

// 첨부 파일 이미지 표시
function imageshow(obj) {
	var str = "";

	var savePath = obj.savePath.replaceAll("\\", "/")

	var fileCallPath = encodeURIComponent(savePath
		+ "/s_" + obj.saveName + "_"
		+ obj.originalFileName + "." + obj.ext);

	str += `<div class="col-4 col-lg-4 item pl-4 my-3">`;

	var originPath = fileCallPath;

	originPath = originPath.replace(new RegExp(/\\/g),
		"/");

	str += `<img src='/display?fileName=${originPath}' class="img-fluid img-thumbnail">`;
	str += `</div>`;
	
	return str;
}