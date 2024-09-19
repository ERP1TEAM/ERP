CKEDITOR.replace('editor', {
    removePlugins: 'uploadimage,uploadfile,filebrowser'
});

// 폼 제출 시 HTML 태그 제거
document.querySelector('form').addEventListener('submit', function(event) {
    var editorData = CKEDITOR.instances.editor.getData();
    var strippedData = editorData.replace(/<\/?[^>]+(>|$)/g, "");  // HTML 태그 제거
    CKEDITOR.instances.editor.setData(strippedData);
});