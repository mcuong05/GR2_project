import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { Card, Spin, Tag, Typography } from 'antd'

interface PingResponse {
  status: string
  database: string
}

export default function App() {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['ping'],
    queryFn: async () => (await axios.get<PingResponse>('/api/ping')).data,
  })

  return (
    <div style={{ padding: 24 }}>
      <Card title="GR2 Shop Manager" style={{ maxWidth: 420 }}>
        <Typography.Paragraph>Trạng thái hệ thống</Typography.Paragraph>
        {isLoading && <Spin />}
        {isError && <Tag color="red">Không gọi được backend</Tag>}
        {data && (
          <>
            <Tag color="green">API: {data.status}</Tag>
            <Tag color={data.database === 'up' ? 'green' : 'red'}>
              Database: {data.database}
            </Tag>
          </>
        )}
      </Card>
    </div>
  )
}