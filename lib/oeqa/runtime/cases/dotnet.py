import os

from oeqa.runtime.case import OERuntimeTestCase
from oeqa.core.decorator.depends import OETestDepends
from oeqa.core.decorator.data import skipIfNotFeature
from oeqa.oetest import oeRuntimeTest, skipModule

class DotnetCompileTest(OERuntimeTestCase):

    @OETestDepends(['ssh.SSHTest.test_ssh'])
    def test_executable_check_dotnet(self):
        status, output = self.target.run('dotnet --info')
        msg = 'running dotnet --info, output: %s' % output
        self.assertEqual(status, 0, msg=msg)

    @OETestDepends(['ssh.SSHTest.test_ssh'])
    def test_executable_compile_and_run_cmdline(self):
        status, output = self.target.run('/opt/dotnet-helloworld/dotnet-helloworld')
        msg = 'running compiled file failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        self.assertEqual(output, 'Hello, World!', msg=msg)
