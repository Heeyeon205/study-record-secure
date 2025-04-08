document.querySelector('table')?.addEventListener('click', event => {
    if(event.target.dataset['id']){
        const id = event.target.dataset['id'];
        fetch(`/member/${id}`, {
            method: 'DELETE'
        })
            .then((response) => {
                if (response.ok) {
                    // 삭제 성공 시 페이지 업데이트 또는 메시지 표시
                    alert("삭제되었습니다.");
                    location.reload(); // 페이지 새로고침
                } else {
                    alert("삭제에 실패했습니다.");
                }
            })
            .catch((error) => {
                console.error("삭제 중 오류 발생:", error);
                alert("삭제 중 오류가 발생했습니다.");
            });
    }


});