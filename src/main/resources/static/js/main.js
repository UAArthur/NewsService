function createPosts(postData) {
    const postsContainer = document.querySelector('.post-wrapper');
    if (!postsContainer) return;

    postsContainer.innerHTML = '';

    postData.forEach(post => {
        const link = document.createElement('a');
        link.href = `news/${post.id}`;
        link.className = 'post-link';

        const postElement = document.createElement('div');
        postElement.className = 'post';

        const postHeader = document.createElement('div');
        postHeader.className = 'post-header';

        const postCategory = document.createElement('span');
        postCategory.className = 'post-category inter-extra-bold';
        postCategory.textContent = post.category.toUpperCase();

        const postLine = document.createElement('span');
        postLine.className = 'post-line';
        postLine.dataset.category = post.category;

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
    const categories = ['announcements', 'events', 'updates', 'shop', 'maintenance'];
    const platforms = ['Client', 'Server'];
    const categoryMapping = {
        'news': 'announcements',
        'events': 'events',
        'updates': 'updates',
        'shop': 'shop',
        'maintenance': 'maintenance'
    };

    //For testing purposes only
    const postData = [];
    for (let i = 0; i < 10; i++) {
        postData.push({
            id: `post${i + 1}`,
            category: categories[i % categories.length],
            platform: platforms[i % platforms.length],
            title: `Post Title ${i + 1}`,
            createdAt: `2021-01-${String(i + 1).padStart(2, '0')}`
        });
    }
    createPosts(postData);

    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');
    const currentCategory = (categoryMapping[category] == null ) ? 'all' : categoryMapping[category];
    const buttons = document.querySelectorAll('.categories a');
    buttons.forEach(button => {
        if (button.dataset.category === currentCategory) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }
    });
});


