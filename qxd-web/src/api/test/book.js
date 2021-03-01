import request from '@/utils/request'
const baseUr='api/Book'
export function add(data) {
  return request({
    url: baseUr,
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url:baseUr,
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: baseUr,
    method: 'put',
    data
  })
}

export default { add, edit, del }
