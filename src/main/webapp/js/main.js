import { initFileUploadModule } from "/js/fileUpload.js";

document.addEventListener("DOMContentLoaded", function () {
    // 🔹 썸네일 업로드 기능 활성화
    initFileUploadModule({
        inputSelector: "#thumbnail-inputs",
        listSelector: "#thumbnail-list",
        addButtonSelector: "#add-thumbnail",
        maxFiles: 5
    });

    // 🔹 일반 첨부파일 업로드 기능 활성화
    initFileUploadModule({
        inputSelector: "#file-inputs",
        listSelector: "#file-list",
        addButtonSelector: "#add-file",
        maxFiles: 5
    });
});
