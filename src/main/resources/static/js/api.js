export function fetchPostById(postId) {
    return fetch(`/api/news/${postId}`)
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching post:', error);
            throw error;
        });
}

export function fetchLatestPosts() {
    return fetch('/api/news/latest')
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching latest posts:', error);
            throw error;
        });
}