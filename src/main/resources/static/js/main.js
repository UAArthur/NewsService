import { fetchPostById, fetchLatestPosts } from './api.js';

const categoryMapping = {
    0: 'ALL',
    1: 'ANNOUNCEMENTS',
    2: 'MAINTENANCE',
    3: 'EVENTS',
    4: 'UPDATES',
    5: 'SHOP'
};

function createPosts(postData) {
    const postsContainer = document.querySelector('.post-wrapper');
    if (!postsContainer) return;

    postsContainer.innerHTML = '';

    postData.forEach(post => {
        const link = document.createElement('a');
        link.href = `${post.id}`;
        link.className = 'post-link';

        const postElement = document.createElement('div');
        postElement.className = 'post';

        const postHeader = document.createElement('div');
        postHeader.className = 'post-header';

        const postCategory = document.createElement('span');
        postCategory.className = 'post-category inter-extra-bold';
        postCategory.textContent = categoryMapping[post.category];

        const postLine = document.createElement('span');
        postLine.className = 'post-line';
        postLine.dataset.category = categoryMapping[post.category];

        const postPlatform = document.createElement('span');
        postPlatform.className = 'post-platform inter-medium';
        postPlatform.textContent = post.platform;

        postHeader.appendChild(postCategory);
        postHeader.appendChild(postLine);
        postHeader.appendChild(postPlatform);

        const postContent = document.createElement('div');
        postContent.className = 'post-content';

        const postTitle = document.createElement('span');
        postTitle.className = 'post-title inter-medium';
        postTitle.textContent = post.title;

        const postDate = document.createElement('span');
        postDate.className = 'post-date';
        postDate.textContent = post.createdAt;

        postContent.appendChild(postTitle);
        postContent.appendChild(postDate);

        postElement.appendChild(postHeader);
        postElement.appendChild(postContent);

        link.appendChild(postElement);
        postsContainer.appendChild(link);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    fetchLatestPosts().then(postData => {
        createPosts(postData);
    });

    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category')?.toUpperCase();
    const currentCategory = category !== undefined ? category : 'ALL';
    const buttons = document.querySelectorAll('.categories a');

    buttons.forEach(button => {
        if (button.dataset.category === currentCategory) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }
    });
});