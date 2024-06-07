document.getElementById('addBrandForm').addEventListener('submit', addBrand);
document.getElementById('updateBrandForm').addEventListener('submit', updateBrand);
document.getElementById('deleteBrandForm').addEventListener('submit', deleteBrand);
document.getElementById('loadTableBtn').addEventListener('click', loadTable);
document.getElementById('loadCheapestBrandsButton').addEventListener('click', fetchCheapestData);

const lowHighBrandTable = document.getElementById('lowHighBrandTable');
const loadLowHighBrandTableButton = document.getElementById('loadLowHighBrandTableButton');
const categoryDropdown = document.getElementById('categoryDropdown');

loadLowHighBrandTableButton.addEventListener('click', () => {
  const selectedCategory = categoryDropdown.value;
  fetch(`/v1/category/minmax?name=${selectedCategory}`) // Replace with your actual API endpoint
    .then(response => response.json())
    .then(data => {
    if (data.status === 200) {
      fillTableWithData(data.data);
    } else {
      console.error('Error fetching data:', data.message);
    }
  })
    .catch(error => console.error('Error:', error));
});

function fillTableWithData(categoryData) {
  // Clear existing table rows
  const tableBody = document.querySelector('#lowHighBrandTable tbody')
  tableBody.innerHTML = '';

  // Create and append table rows for each category
  const row = lowHighBrandTable.insertRow();
  const categoryCell = row.insertCell();
  const lowestPriceBrandCell = row.insertCell();
  const lowestPriceCell = row.insertCell();
  const highestPriceBrandCell = row.insertCell();
  const highestPriceCell = row.insertCell();

  categoryCell.textContent = categoryData.category;
  highestPriceBrandCell.textContent = categoryData.highestPrice.brand;
  highestPriceCell.textContent = categoryData.highestPrice.price;
  lowestPriceBrandCell.textContent = categoryData.lowestPrice.brand;
  lowestPriceCell.textContent = categoryData.lowestPrice.price;
}

// Populate category dropdown (assuming you have category data available)
const categories = ['상의', '아우터', '바지', '스니커즈', '가방', '모자', '양말', '액세서리'];
categories.forEach(category => {
  const option = document.createElement('option');
  option.value = category;
  option.textContent = category;
  categoryDropdown.appendChild(option);
});


function fetchCheapestData() {
    fetch('/v1/brands/cheapest-total')
        .then(response => response.json())
        .then(data => {
            renderCheapestTable('#cheapestBrandTable tbody', data);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            renderDefaultTableBody('#cheapestBrandTable tbody', false);
        });
}

function renderCheapestTable(target, data) {
    const tableBody = document.querySelector(target);
    tableBody.innerHTML = '';

    const brand = data.data.brand;
    const categories = data.data.categories;
    categories.forEach(item => {
        const categoryRow = createCategoryRow(item.category, brand, item.price);
        tableBody.appendChild(categoryRow);
    });
    // 총합을 추가해야함
}


function loadTable() {
  fetch('/v1/categories/cheapest')
    .then(response => response.json())
    .then(data => {
      if (data.status === 200 && data.message === 'OK') {
        renderTableBody('#brandTable tbody', data.data);
      } else {
        console.error(`Error: ${data.message}`);
        renderDefaultTableBody('#brandTable tbody');
      }
    })
    .catch(error => {
      console.error(error);
      renderDefaultTableBody('#brandTable tbody');
    });
}

function createCategoryRow(category, brand = '-', price = '-') {
  const categoryRow = document.createElement('tr');
  const categoryCell = document.createElement('td');
  categoryCell.textContent = category;
  categoryRow.appendChild(categoryCell);

  const brandCell = document.createElement('td');
  const priceCell = document.createElement('td');
  brandCell.textContent = brand;
  priceCell.textContent = price;
  categoryRow.appendChild(brandCell);
  categoryRow.appendChild(priceCell);

  return categoryRow;
}

function createTotalRow(totalPrice) {
  const totalRow = document.createElement('tr');
  const totalLabelCell = document.createElement('td');
  totalLabelCell.textContent = '총합';
  totalLabelCell.colSpan = 2;
  totalRow.appendChild(totalLabelCell);

  const totalPriceCell = document.createElement('td');
  totalPriceCell.textContent = totalPrice;
  totalRow.appendChild(totalPriceCell);

  return totalRow;
}

function renderTableBody(target, data) {
  const tableBody = document.querySelector(target);
  const categories = ['상의', '아우터', '바지', '스니커즈', '가방', '모자', '양말', '액세서리'];
  tableBody.innerHTML = '';

  categories.forEach(category => {
    const categoryData = data.categories.find(item => item.category === category);
    if (categoryData) {
      tableBody.appendChild(createCategoryRow(category, categoryData.lowest_price_brand, categoryData.lowest_price));
    } else {
      tableBody.appendChild(createCategoryRow(category));
    }
  });

  // 총합 행 추가
  const totalRow = createTotalRow(data.total_lowest_price_sum);
  tableBody.appendChild(totalRow);
}

function renderDefaultTableBody(target, needTotal=true) {
  const tableBody = document.querySelector(target);
  const categories = ['상의', '아우터', '바지', '스니커즈', '가방', '모자', '양말', '액세서리'];
  tableBody.innerHTML = '';

  categories.forEach(category => {
    tableBody.appendChild(createCategoryRow(category));
  });

  // 총합 행 추가
  if (needTotal) {
    const totalRow = createTotalRow('-');
    tableBody.appendChild(totalRow);
  }
}


function addBrand(event) {
  event.preventDefault();
  const formData = new FormData(event.target);
  const brandData = getBrandData(formData, 'add');

  fetch('v1/brands', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(brandData)
  })
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error(error));
}

function updateBrand(event) {
  event.preventDefault();
  const formData = new FormData(event.target);
  const brandData = getBrandData(formData, 'update');

  fetch(`/v1/brands`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(brandData)
  })
  .then(response => {
    if (response.ok) {
      console.log(`브랜드 ${brandData.brand}이(가) 수정되었습니다.`);
    } else {
      console.error(`브랜드 수정에 실패했습니다: ${response.status}`);
    }
  })
  .catch(error => console.error(error));
}

function deleteBrand(event) {
  event.preventDefault();
  const formData = new FormData(event.target);
  const brandName = formData.get('deleteBrandName');
  const categories = getCategories(formData, 'delete');

  const requestData = { brand: brandName, categories };

  fetch(`v1/brands`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestData)
  })
  .then(response => {
    if (response.ok) {
      console.log(`브랜드 ${brandName}이(가) 삭제되었습니다.`);
    } else {
      console.error(`브랜드 삭제에 실패했습니다: ${response.status}`);
    }
  })
  .catch(error => console.error(error));
}

function getBrandData(formData, prefix) {
  const brandName = formData.get(`${prefix}BrandName`);
  const categories = getCategories(formData, prefix);
  return { brand: brandName, categories };
}

function getCategories(formData, prefix) {
  return [
    { category: '상의', price: formData.get(`${prefix}Top`) },
    { category: '스니커즈', price: formData.get(`${prefix}Bottom`) },
    { category: '아우터', price: formData.get(`${prefix}Outer`) },
    { category: '바지', price: formData.get(`${prefix}Pants`) },
    { category: '가방', price: formData.get(`${prefix}Bag`) },
    { category: '모자', price: formData.get(`${prefix}Cap`) },
    { category: '양말', price: formData.get(`${prefix}Socks`) },
    { category: '액세서리', price: formData.get(`${prefix}Accessory`) }
  ];
}