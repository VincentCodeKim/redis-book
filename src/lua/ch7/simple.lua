local function main(ARGV)
	local result = redis.call('set', KEYS[1], ARGV[1] + ARGV[2])
	return result
end
print(main())
