/**
 * img.js
 */


// 크기&확장자 제한
$('input[name="uploadFile"]').attr('accept', '.jpeg, .png, .gif, .jpg, .psd, .eps, .ai, .tiff, .bmp, .svg, .jfif, .webp');

var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

var maxSize = 1048576; // 5MB

var requestImg = '';

// 파일 첨부 초기화
function fileUploadClick() {

	$('input[name="uploadFile"]').val('');
	$('.uploadResult .row div').remove();
};

function checkExtentsion(fileName, fileSize) {
	if (fileSize >= maxSize) {
		sweetModalError("파일 사이즈가 초과되었습니다");
		return false;
	}

	if (regex.test(fileName)) {
		sweetModalError("해당 종류의 파일은 업로드할 수 없습니다.")
		return false;
	}
	return true;
}
function fileList() {

	var x = document.getElementById("multiFile");

	if ('files' in x) {
		if (x.files.length > 5) {
			sweetModalError("파일 개수가 초과되었습니다.");
			document.getElementById("multiFile").value = "";
			return;
		}
	}

	var formData = new FormData();

	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;

	if (requestImg != '') {
		deleteFile(requestImg);
		requestImg = '';
	}

	// formData에 데이터 넣기
	for (var i = 0; i < files.length; i++) {

		if (!checkExtentsion(files[i].name, files[i].size)) {
			return false;
		}
		if (files[i].type.indexOf('image') == -1) {
			sweetModalError("이미지 파일을 등록해 주세요.")
			return false;
		}

		formData.append("uploadFile", files[i]);
	}

	// Ajax
	$.ajax({
		url: '/request/fileInsert',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'
		, success: function(result) {
			showUpFile(result);
			if (result != null) {
				requestImg = result;
			}
		}
	})
};
// 이미지 표시
function showUpFile(uploadResultArr) {

	if (!uploadResultArr || uploadResultArr.length == 0)
		return;

	var uploadUL = $('.uploadResult .row');

	var str = "";

	$(uploadResultArr)
		.each(
			function(i, obj) {
				str += imageshow(obj);
			});

	uploadUL.append(str);
}
// 첨부 파일 이미지 표시
function imageshow(obj) {
	var str = "";

	var savePath = obj.savePath.replaceAll("\\", "/")

	var fileCallPath = encodeURIComponent(savePath
		+ "/s_" + obj.saveName + "_"
		+ obj.originalFileName + "." + obj.ext);

	str += `<div class="col-2 col-lg-2 item pl-4 my-3">`;

	var originPath = fileCallPath;

	originPath = originPath.replace(new RegExp(/\\/g),
		"/");

	str += `<img src='/display?fileName=${originPath}' class="img-fluid img-thumbnail">`;
	str += `</div>`;
	console.log('dfsd');
	return str;
}

function insert() {
	var formData = new FormData(document.insertRequest);

	for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}

	$.ajax({
		url: '/request/insert',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'
		, success: function(result) {
			console.log(result);
			if (requestImg != '') {
				deleteFile(requestImg);
				requestImg = '';
			}
			Swal.fire({
			  text:'의뢰가 정상적으로 등록되었습니다.',
			  icon:'success'
			});
			location.href = "/request/list"
		}

	})
}

function deleteRequest(rno) {

	if(proposalList.length == 0){
		
			Swal.fire({
				   title: '정말 삭제하시겠습니까?',
				   text: '삭제 시 해당 게시글은 복구할 수 없습니다.',
				   icon: 'question',
				   
				   showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
				   confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
				   cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
				   confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
				   cancelButtonText: '취소', // cancel 버튼 텍스트 지정

				}).then(result => {
				   // 만약 Promise리턴을 받으면,
				   if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
					   $.ajax({
							url: '/request/delete?requestNo=' + rno,
							type: 'Get'
							, success: function() {
		
								if (uploadImg != null) {
									deleteFile(uploadImg);
								}
					      		Swal.fire('정상적으로 삭제되었습니다.', '', 'success');
							    location.href = "/request/list";
					   		}
					   })				
	
			}
		})
		
	}else if(proposalList.length > 0){
		if(contract == null){
			Swal.fire({
				   title: '정말 삭제하시겠습니까?',
				   text: '삭제 시 해당 게시글은 복구할 수 없습니다.',
				   icon: 'question',
				   
				   showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
				   confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
				   cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
				   confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
				   cancelButtonText: '취소', // cancel 버튼 텍스트 지정

				}).then(result => {
				   // 만약 Promise리턴을 받으면,
				   if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
				   		$.ajax({
							url: '/request/stateUpdate?requestNo=' + rno,
							type: 'post'
							, success: function() {
				      		Swal.fire('정상적으로 삭제되었습니다.', '', 'success');
				   		}
				   })
				   .then(function(){					
						location.href = "/request/list";
					
				});
	
			}
		})
			
		}else if(contract != null){
			
			Swal.fire({
				  text: '계약이 진행된 의뢰는 삭제가 불가능합니다.', 
				  title: '삭제가 불가능한 게시글입니다.',    
				  icon: 'error',              
			});
		}
	}
}
function deleteFile(img) {
	$.ajax({
		url: '/request/fileDelete',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(img),
		async: false
	})
}
function sweetModalSuccess(text) {
	Swal.fire({
		icon: "success",
		title: text
	});
}
function sweetModalError(text) {
	Swal.fire({
		icon: "error",
		title: "다시 확인해 주세요",
		text: text
	});
}