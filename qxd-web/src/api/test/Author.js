import request from '@/utils/request'

const baseUrl ='api/Author'

export function add(data) {
  return request({
    url: baseUrl,
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: baseUrl,
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: baseUrl,
    method: 'put',
    data
  })
}

export default { add, edit, del }
