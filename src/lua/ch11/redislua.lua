local sum = ARGV[1] + ARGV[2]
local result = redis.call('set', KEYS[1], sum)
return result
