export function initFileUploadModule({ inputSelector, listSelector, addButtonSelector, maxFiles = 5 }) {
    const fileList = document.querySelector(listSelector);
    const fileInputs = document.querySelector(inputSelector);
    const addFileButton = document.querySelector(addButtonSelector);

    // 파일 추가 버튼 클릭 시
    addFileButton.addEventListener("click", function () {
        if (fileInputs.querySelectorAll(".file-input").length < maxFiles) {
            const newInput = document.createElement("input");
            newInput.setAttribute("type", "file");
            newInput.classList.add("file-input");

            const fileWrapper = document.createElement("div");
            fileWrapper.classList.add("file-item");

            const removeButton = document.createElement("button");
            removeButton.innerText = "삭제";
            removeButton.type = "button";
            removeButton.classList.add("delete-file");

            removeButton.addEventListener("click", function () {
                fileWrapper.remove();
            });

            fileWrapper.appendChild(newInput);
            fileWrapper.appendChild(removeButton);
            fileInputs.appendChild(fileWrapper);
        } else {
            alert(`파일은 최대 ${maxFiles}개까지 업로드할 수 있습니다.`);
        }
    });

    // 기존 파일 삭제 버튼 클릭 시
    fileList.addEventListener("click", function (event) {
        if (event.target.classList.contains("delete-file")) {
            event.target.parentElement.remove();
        }
    });
}
